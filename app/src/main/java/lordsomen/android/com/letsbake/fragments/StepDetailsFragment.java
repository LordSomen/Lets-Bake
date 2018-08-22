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
    private static final String SELECTED_POSITION = "video_position";
    private static final String SELECTED_WINDOW = "video_window";
    @BindView(R.id.fragment_step_playerView)
    public SimpleExoPlayerView stepPlayerView;
    @BindView(R.id.fragment_step_description)
    public TextView stepDescription;
    private Step stepData;
    private SimpleExoPlayer player;
    private String videoUrl;
    private long startVideoPosition;
    private int startVideoWindow;


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
        if (null != getArguments())
            stepData = getArguments().getParcelable(VAL);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container,
                             @Nullable Bundle savedInstanceState) {
        View rootView = inflater.inflate(R.layout.fragment_step_details, container, false);
        ButterKnife.bind(this, rootView);
        if(null!= stepData) {
            stepDescription.setText(stepData.getDescription());
            videoUrl = stepData.getVideoURL();
        }
        if (savedInstanceState != null) {
            startVideoPosition = savedInstanceState.getLong(SELECTED_POSITION);
            startVideoWindow = savedInstanceState.getInt(SELECTED_WINDOW);

        }
        if (videoUrl != null && !videoUrl.equals("")) {

            initializePlayer(videoUrl);
            stepPlayerView.setVisibility(View.VISIBLE);
        } else {
            release_player();
            ViewGroup.LayoutParams params = stepDescription.getLayoutParams();
            params.height = ViewGroup.LayoutParams.MATCH_PARENT;
            params.width = ViewGroup.LayoutParams.MATCH_PARENT;
            stepDescription.setLayoutParams(params);
        }
        return rootView;
    }

    private void initializePlayer(String videoUrl) {
        if (null == player) {
            DefaultBandwidthMeter bandwidthMeter = new DefaultBandwidthMeter();
            TrackSelection.Factory videoTrackSelectionFactory =
                    new AdaptiveTrackSelection.Factory(bandwidthMeter);
            TrackSelector trackSelector =
                    new DefaultTrackSelector(videoTrackSelectionFactory);

            player = ExoPlayerFactory.newSimpleInstance(getContext(), trackSelector);
            stepPlayerView.setPlayer(player);
            // Produces DataSource instances through which media data is loaded.
            String userAgent = Util.getUserAgent(getContext(), "ClassicalMusicQuiz");
            if (null != getContext()) {
                MediaSource mediaSource = new ExtractorMediaSource(Uri.parse(videoUrl), new DefaultDataSourceFactory(
                        getContext(), userAgent), new DefaultExtractorsFactory(), null, null);
                // Prepare the player with the source.
                player.prepare(mediaSource);
                player.setPlayWhenReady(true);
                if (startVideoPosition != 0)
                    player.seekTo(startVideoPosition);
            }
        }
    }

    public void release_player() {
        if (null != player) {
            updateStartPosition();
            player.stop();
            player.release();
            player = null;
        }
    }
    private void updateStartPosition() {
        if (player != null) {
            startVideoWindow = player.getCurrentWindowIndex();
            startVideoPosition = Math.max(0, player.getContentPosition());
        }
    }

    @Override
    public void onStop() {
        super.onStop();
        if (Util.SDK_INT > 23) release_player();
    }

    @Override
    public void onPause() {
        super.onPause();
        if(player != null)
            player.getPlayWhenReady();
        if (Util.SDK_INT <= 23) release_player();
    }

    @Override
    public void onStart() {
        super.onStart();
        if (Util.SDK_INT > 23 && null != videoUrl && !videoUrl.equals("")) {
            initializePlayer(videoUrl);
        }
    }

    @Override
    public void onResume() {
        super.onResume();
        if (Util.SDK_INT <= 23 && null != videoUrl && !videoUrl.equals("")) {
            initializePlayer(videoUrl);
        }
    }


    @Override
    public void onSaveInstanceState(@NonNull Bundle outState) {
        super.onSaveInstanceState(outState);
        updateStartPosition();
        outState.putLong(SELECTED_POSITION, startVideoPosition);
        outState.putInt(SELECTED_WINDOW, startVideoWindow);
    }
}
