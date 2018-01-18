package it.almaviva.roomtestapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.databinding.BindingAdapter;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.renderscript.ScriptGroup;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import it.almaviva.roomtestapp.databinding.ActivityMainBinding;
import it.almaviva.roomtestapp.entity.Message;
import it.almaviva.roomtestapp.utils.MessageUtil;
import it.almaviva.roomtestapp.viewModel.MyViewModel;

public class MainActivity extends AppCompatActivity {

	private static final String TAG = "MainActivity";

	private ActivityMainBinding mBinding;
	private MsgAdapter mAdapter;
	private MyViewModel mViewModel;
	private boolean mIsObserving;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);

		mBinding = DataBindingUtil.setContentView(this, R.layout.activity_main);

		setSupportActionBar(mBinding.toolbar);

		mBinding.fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				mViewModel.insertMessage(MessageUtil.getRandom());
			}
		});

		mBinding.contentMain.etSearch.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				mViewModel.setSearchString(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		} );

		mViewModel = ViewModelProviders.of(this).get(MyViewModel.class);

		mAdapter = new MsgAdapter();

		observeMessageList();

		mBinding.contentMain.rvMain.setAdapter(mAdapter);

		mBinding.contentMain.setViewmodel(mViewModel);
	}

	@Override
	public boolean onCreateOptionsMenu(Menu menu) {
		// Inflate the menu; this adds items to the action bar if it is present.
		getMenuInflater().inflate(R.menu.menu_main, menu);
		return true;
	}

	@Override
	public boolean onOptionsItemSelected(MenuItem item) {
		// Handle action bar item clicks here. The action bar will
		// automatically handle clicks on the Home/Up button, so long
		// as you specify a parent activity in AndroidManifest.xml.
		int id = item.getItemId();

		//noinspection SimplifiableIfStatement
		if (id == R.id.action_observe) {
			if (mIsObserving) {
				ignoreMessageList();
			} else {
				observeMessageList();
			}
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

	private void observeMessageList() {
		mViewModel.getMessageList().observe(MainActivity.this, mMessageListObserver);
		mIsObserving = true;
	}

	private void ignoreMessageList() {
		mViewModel.getMessageList().removeObserver(mMessageListObserver);
		mIsObserving = false;
	}

	private final Observer<PagedList<Message>> mMessageListObserver = new Observer<PagedList<Message>>() {
		@Override
		public void onChanged(@Nullable PagedList<Message> messages) {
			if (messages == null) {
				Log.i(TAG, "No messages, yet");
			} else {
				Log.i(TAG, "Messages: " + messages.size());
			}
			mAdapter.setList(messages);
		}
	};

	static class MessageViewHolder extends RecyclerView.ViewHolder {

		TextView timeStamp;
		TextView body;

		public MessageViewHolder(View itemView) {
			super(itemView);
			timeStamp = (TextView)itemView.findViewById(android.R.id.text2);
			body = (TextView)itemView.findViewById(android.R.id.text1);
		}

		public void bindTo(Message msg) {
			timeStamp.setText(msg.timeStamp.toString());
			body.setText(msg.body);
		}

		public void clear() {
			timeStamp.setText("");
			body.setText("");
		}
	}

	public static class MsgAdapter extends PagedListAdapter<Message, MessageViewHolder> {

		LayoutInflater mInflater = null;

		public MsgAdapter() {
			super(DIFF_CALLBACK);
		}

		@Override
		public MessageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
			if (mInflater == null) {
				mInflater = LayoutInflater.from(parent.getContext());
			}
			return new MessageViewHolder(mInflater.inflate(android.R.layout.simple_list_item_2, parent, false));
		}

		@Override
		public void onBindViewHolder(MessageViewHolder holder, int position) {
			Message msg = getItem(position);
			if (msg != null) {
				holder.bindTo(msg);
			} else {
				// Null defines a placeholder item - PagedListAdapter will automatically invalidate
				// this row when the actual object is loaded from the database
				holder.clear();
			}
		}

		public static final DiffCallback<Message> DIFF_CALLBACK = new DiffCallback<Message>() {
			@Override
			public boolean areItemsTheSame(@NonNull Message oldMsg, @NonNull Message newMsg) {
				// User properties may have changed if reloaded from the DB, but ID is fixed
				return oldMsg.id == newMsg.id;
			}
			@Override
			public boolean areContentsTheSame(@NonNull Message oldMsg, @NonNull Message newMsg) {
				// NOTE: if you use equals, your object must properly override Object#equals()
				// Incorrectly returning false here will result in too many animations.
				return oldMsg.equals(newMsg);
			}
		};
	}
}
