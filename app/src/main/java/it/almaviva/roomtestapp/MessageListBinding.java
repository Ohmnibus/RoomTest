package it.almaviva.roomtestapp;

import android.arch.lifecycle.LiveData;
import android.arch.paging.PagedList;
import android.databinding.BindingAdapter;
import android.support.v7.widget.RecyclerView;

import it.almaviva.roomtestapp.entity.Message;

/**
 * Created by m.spalletti on 18/01/2018.
 */

/**
 * Contains {@link BindingAdapter}s for the {@link Message} list.
 */
public class MessageListBinding {
	/**
	 * Bind a list of {@link Message}s to {@link RecyclerView}.<br>
	 * Not working wit {@link LiveData}.
	 * @param recyclerView
	 * @param items
	 */
	@SuppressWarnings("unchecked")
	@BindingAdapter("items")
	public static void setItems(RecyclerView recyclerView, PagedList<Message> items) {
		MainActivity.MsgAdapter adapter = (MainActivity.MsgAdapter) recyclerView.getAdapter();
		if (adapter != null) {
			//adapter.replaceData(items);
			adapter.setList(items);
		}
	}
}
