package lordsomen.android.com.letsbake.fragments;

import android.net.Uri;
import android.os.Bundle;
import android.support.annotation.NonNull;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.google.android.exoplayer2.ExoPlayerFactory;
import com.google.android.exoplayer2.SimpleExoPlayer;
import com.google.android.exoplayer2.extractor.DefaultExtractorsFactory;
import com.google.android.exoplayer2.source.ExtractorMediaSource;
import com.google.android.exoplayer2.source.MediaSource;
import com.google.android.exoplayer2.trackselection.AdaptiveTrackSelection;
import com.google.android.exoplayer2.trackselection.DefaultTrackSelector;
import com.google.android.exoplayer2.trackselection.TrackSelection;
import com.google.android.exoplayer2.trackselection.TrackSelector;
import com.google.android.exoplayer2.ui.SimpleExoPlayerView;
import com.google.android.exoplayer2.upstream.DefaultBandwidthMeter;
import com.google.android.exoplayer2.upstream.DefaultDataSourceFactory;
import com.google.android.exoplayer2.util.Util;

import butterknife.BindView;
import butterknife.ButterKnife;
import lordsomen.android.com.letsbake.R;
import lordsomen.android.com.letsbake.pojos.Step;

/**
 * Created by soumyajit on 25/3/18.
 */

public class StepDetailsFragment extends Fragment {

    public static final String VAL = "val";
    @BindView(R.id.fragment_step_playerView)
    public SimpleExoPlayerView stepPlayerView;
    @BindView(R.id.fragment_step_description)
    public TextView stepDescription;
    private Step stepData;
    private SimpleExoPlayer player;


    public static StepDetailsFragment init(Step step) {
        StepDetailsFragment stepDetailsFragment = new StepDetailsFragment();
        // Supply val input as an argument. 

        Bundle args = new Bundle();
        args.putParcelable(VAL, step);
        stepDetailsFragment.setArguments(args);
        return stepDetailsFragment;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if(null != getArguments())
        stepData = getArguments().getParcelable(VAL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details,container,false);
        ButterKnife.bind(this,rootView);
        stepDescription.setText(stepData.getDescription());
        String videoUrl = stepData.getVideoURL();
        if(videoUrl != null) initializePlayer(videoUrl);
        return rootView;
    }

    private void initializePlayer(String videoUrl) {
        if(null == player) {
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            stepPlayerView.setPlayer(player);
            // Produces DataSource instances through which media data is loaded.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            if (null != getContext()){
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
            // Prepare the player with the source.
            player.prepare(mediaSource);
            player.setPlayWhenReady(true);
            }
        }
    }

    public void release_player(){
        player.stop();
        player.release();
        player = null;
    }

    @Override
    public void onStop() {
        super.onStop();
        release_player();
    }
}
