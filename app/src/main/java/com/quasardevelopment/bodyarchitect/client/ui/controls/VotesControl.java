package com.quasardevelopment.bodyarchitect.client.ui.controls;

import android.accounts.NetworkErrorException;
import android.content.Context;
import android.util.AttributeSet;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.*;
import com.nostra13.universalimageloader.core.ImageLoader;
import com.nostra13.universalimageloader.core.listener.PauseOnScrollListener;
import com.quasardevelopment.bodyarchitect.R;
import com.quasardevelopment.bodyarchitect.client.model.IRatingable;
import com.quasardevelopment.bodyarchitect.client.ui.BAMessageBox;
import com.quasardevelopment.bodyarchitect.client.ui.adapters.RatingListAdapter;
import com.quasardevelopment.bodyarchitect.client.wcf.Wsdl2Code.WebServices.BasicHttpBinding_IBodyArchitectAccessService.*;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: robocik
 * Date: 14.05.13
 * Time: 09:12
 * To change this template use File | Settings | File Templates.
 */
public class VotesControl extends LinearLayout    implements AbsListView.OnScrollListener
{
    IRatingable entry;
    boolean refreshRequired;
    PagedResultOfCommentEntryDTO5oAtqRlh result;
    boolean loaded;
    TextView tbNoRatings;
    ListView lstRatings;
    LinearLayout progressPane;
    TextView tbProgressText;
    ProgressBar progressBar;
    boolean isLoading;

    public VotesControl(Context context, AttributeSet attrs) {
        super(context, attrs);

        LayoutInflater layoutInflater = (LayoutInflater) context
                .getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        layoutInflater.inflate(R.layout.votes_control, this);

        tbNoRatings= (TextView) findViewById(R.id.votes_control_empty);
        lstRatings= (ListView) findViewById(R.id.votes_control_ratings_list);
        progressPane= (LinearLayout) findViewById(R.id.ProgressPane);
        tbProgressText= (TextView) findViewById(R.id.tbProgress);
        progressBar = (ProgressBar)findViewById(R.id.progressBar);
//        pnlSyncNeeded=(LinearLayout)findViewById(R.id.pnlSyncNeeded);
//        pnlMain= (TableLayout)findViewById(R.id.pnlMain);
        lstRatings.setOnScrollListener(new PauseOnScrollListener(ImageLoader.getInstance(),false, false,this));
    }

    public boolean isLoaded()
    {
        return loaded;
    }

    public boolean isRefreshRequired ()
    {
        return refreshRequired;
    }

    void showProgress(boolean show,int text)
    {
        if(show)
        {
            progressPane.setVisibility(VISIBLE);
            tbProgressText.setText(text);
        }
        else
        {
            progressPane.setVisibility(GONE);
        }
    }

    public void Load(IRatingable exercise)
    {
        if(loaded && !refreshRequired && entry!=null && entry.getGlobalId().equals(exercise.getGlobalId()))
        {
            return;
        }
        entry=exercise;
        isLoading=true;
        showProgress(true,R.string.progress_retrieving_items);
        try {
            BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
                @Override
                public void Wsdl2CodeStartedRequest() { }

                @Override
                public void Wsdl2CodeFinished(String methodName, Object Data)
                {
                    result=(PagedResultOfCommentEntryDTO5oAtqRlh)Data;
                    refreshRequired=false;
                    fillComments(result.items);
                    loaded=true;
                }

                @Override
                public void Wsdl2CodeFinishedWithException(Exception ex) {
                    refreshRequired=false;
                    BAMessageBox.ShowToast(R.string.err_unhandled);
                }

                @Override
                public void Wsdl2CodeEndedRequest() {
                    showProgress(false,0);
                    isLoading=false;
                }
            });
            PartialRetrievingInfo info=new PartialRetrievingInfo();
            info.pageSize=40;
            service.GetCommentsAsync(entry.getGlobalId(),info);
        }
        catch(NetworkErrorException ex)
        {
            showProgress(false,0);
            BAMessageBox.ShowToast(R.string.err_network_problem);
        }
        catch(Exception ex)
        {
            showProgress(false,0);
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }

    }

    public void LoadMore()
    {
        showProgress(true,R.string.progress_retrieving_items);
        isLoading=true;
        try {
            BasicHttpBinding_IBodyArchitectAccessService service = new BasicHttpBinding_IBodyArchitectAccessService(new IWsdl2CodeEvents() {
                @Override
                public void Wsdl2CodeStartedRequest() { }

                @Override
                public void Wsdl2CodeFinished(String methodName, Object Data)
                {
                    result=(PagedResultOfCommentEntryDTO5oAtqRlh)Data;

                    RatingListAdapter adapter=(RatingListAdapter)lstRatings.getAdapter();
                    for (Object entry:result.items)
                    {
                        adapter.add((CommentEntryDTO) entry);
                    }
                    adapter.notifyDataSetChanged();
                }

                @Override
                public void Wsdl2CodeFinishedWithException(Exception ex) {
                    refreshRequired=false;
                    BAMessageBox.ShowToast(R.string.err_unhandled);
                }

                @Override
                public void Wsdl2CodeEndedRequest() {
                    showProgress(false,0);
                    isLoading=false;
                }
            });
            PartialRetrievingInfo info=new PartialRetrievingInfo();
            info.pageSize=40;
            info.pageIndex=result.pageIndex+1;
            service.GetCommentsAsync(entry.getGlobalId(),info);
        }
        catch(NetworkErrorException ex)
        {
            showProgress(false,0);
            BAMessageBox.ShowToast(R.string.err_network_problem);
        }
        catch(Exception ex)
        {
            showProgress(false,0);
            BAMessageBox.ShowToast(R.string.err_unhandled);
        }
    }

    void fillComments(List<CommentEntryDTO> items)
    {
         if(items.size()==0)
         {
             tbNoRatings.setVisibility(View.VISIBLE);
             lstRatings.setVisibility(View.GONE);
         }
        else
         {
             tbNoRatings.setVisibility(View.GONE);
             lstRatings.setVisibility(View.VISIBLE);
         }
        RatingListAdapter adapter = new RatingListAdapter(this.getContext(), R.layout.rating_list_item, items);
        lstRatings.setAdapter(adapter);
        adapter.notifyDataSetChanged();
    }

    private boolean hasMore()
    {
        RatingListAdapter adapter=(RatingListAdapter)lstRatings.getAdapter();
        return result!=null && adapter.getCount()<result.allItemsCount;
    }
    @Override
    public void onScrollStateChanged(AbsListView absListView, int i) {}

    public void onScroll(AbsListView view, int firstVisible, int visibleCount, int totalCount) {

        boolean loadMore = /* maybe add a padding */
                firstVisible + visibleCount >= totalCount;

        if(!isLoading && loadMore && hasMore()) {
            LoadMore();
        }
    }

    public void UpdateComments()
    {
        refreshRequired=true;
    }
}
