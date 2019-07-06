package com.widyatama.tvshowtime.core.view;

import com.widyatama.tvshowtime.core.model.video.Video;

public interface VideoView {
    void showVideoData(Video video);
    void showVideoNotFound(String message);
    void showVideoError(String message);
}
