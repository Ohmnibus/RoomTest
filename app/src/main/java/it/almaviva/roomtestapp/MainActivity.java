package it.almaviva.roomtestapp;

import android.arch.lifecycle.Observer;
import android.arch.lifecycle.ViewModelProviders;
import android.arch.paging.PagedList;
import android.arch.paging.PagedListAdapter;
import android.arch.persistence.room.Room;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.design.widget.Snackbar;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.recyclerview.extensions.DiffCallback;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Menu;
import android.view.MenuItem;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import it.almaviva.roomtestapp.dao.MyDatabase;
import it.almaviva.roomtestapp.entity.Message;
import it.almaviva.roomtestapp.utils.MessageUtil;
import it.almaviva.roomtestapp.viewModel.MyViewModel;

public class MainActivity extends AppCompatActivity {

	//MyDatabase mDatabase;
	MsgAdapter mAdapter;
	String mLastText = "";
	MyViewModel viewModel;

	@Override
	protected void onCreate(Bundle savedInstanceState) {
		super.onCreate(savedInstanceState);
		setContentView(R.layout.activity_main);
		Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
		setSupportActionBar(toolbar);

		FloatingActionButton fab = (FloatingActionButton) findViewById(R.id.fab);
		fab.setOnClickListener(new View.OnClickListener() {
			@Override
			public void onClick(View view) {
				viewModel.insertUser(MessageUtil.getRandom());
			}
		});

		EditText search = findViewById(R.id.et_search);
		search.addTextChangedListener(new TextWatcher() {

			@Override
			public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

			}

			@Override
			public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
				viewModel.setSearchString(charSequence.toString());
			}

			@Override
			public void afterTextChanged(Editable editable) {

			}
		} );

		viewModel = ViewModelProviders.of(this).get(MyViewModel.class);

		mAdapter = new MsgAdapter();

		viewModel.getMessageList().observe(MainActivity.this, new Observer<PagedList<Message>>() {
			@Override
			public void onChanged(@Nullable PagedList<Message> messages) {
				mAdapter.setList(messages);
			}
		});


		RecyclerView recyclerView = findViewById(R.id.rv_main);
		recyclerView.setAdapter(mAdapter);
		LinearLayoutManager llm = new LinearLayoutManager(this);
		llm.setOrientation(LinearLayoutManager.VERTICAL);
		recyclerView.setLayoutManager(llm);
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
		if (id == R.id.action_settings) {
			return true;
		}

		return super.onOptionsItemSelected(item);
	}

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

	static class MsgAdapter extends PagedListAdapter<Message, MessageViewHolder> {

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
