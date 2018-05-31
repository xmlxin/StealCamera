# StealCamera
Camera拍照、MediaRecorder录制视频demo


Camera拍摄高清图片
这个和手机又关系，有的手机默认像素是高质量的有的是低质量
一般要自己设置图片尺寸

	  Camera.Parameters parameters = mCamera.getParameters();
	  //获取相机可支持的尺寸
	  List<Camera.Size> photoSizes  = parameters.getSupportedPictureSizes();
	  for (Camera.Size size : photoSizes) {//因为我需要的是4：3的图片，所以设置为4：3图片尺寸。
	        if (size.width / 4 == size.height / 3) {
	            parameters.setPictureSize(size.width, size.height);//设置图片像素尺寸
	            Log.e(TAG, "SET width:" + size.width + " height " + size.height);
	            break;
	        }
        }




录制高清视频:

	 最好采用系统提供的配置文件进行配置录制参数，不容易出问题，也可以采用自定义配置参数来录制
	 CamcorderProfile mProfile = CamcorderProfile.get(CamcorderProfile.QUALITY_HIGH);

自定义配置:

  		// BEGIN_INCLUDE (configure_media_recorder)
        mMediaRecorder = new MediaRecorder();
        mMediaRecorder.reset();
        mIsRecording = true;
        // Step 1: Unlock and set camera to MediaRecorder
        mCamera.unlock();
        mMediaRecorder.setCamera(mCamera);
        mMediaRecorder.setOrientationHint(90);// 输出旋转90度，保持竖屏录制

        // Step 2: Set sources
        mMediaRecorder.setAudioSource(MediaRecorder.AudioSource.DEFAULT);//VOICE_RECOGNITION
        mMediaRecorder.setVideoSource(MediaRecorder.VideoSource.CAMERA);

        // Step 3: Set a Camera Parameters
        mMediaRecorder.setOutputFormat(MediaRecorder.OutputFormat.MPEG_4);
        /* 设置分辨率*/
        mMediaRecorder.setVideoSize(2160, 1080);
        //视频文件的流畅度主要跟VideoFrameRate有关，参数越大视频画面越流畅，但实际中跟你的摄像头质量有很大关系
		//        mMediaRecorder.setVideoFrameRate();
        /* Encoding bit rate: 1 * 1024 * 1024*/
        //设置帧频率，然后就清晰了 清晰度和录制文件大小主要和EncodingBitRate有关，参数越大越清晰，同时录制的文件也越大
        mMediaRecorder.setVideoEncodingBitRate(20 * 1024 * 1024);
        // 视频录制格式
        mMediaRecorder.setVideoEncoder(MediaRecorder.VideoEncoder.H264);
        mMediaRecorder.setAudioEncoder(MediaRecorder.AudioEncoder.AAC);

        // Step 4: Set output file
		//CamcorderProfile mCamcorderProfile = CamcorderProfile.get(Camera.CameraInfo.CAMERA_FACING_BACK,
	//  CamcorderProfile.QUALITY_HIGH);
	//  mMediaRecorder.setProfile(mCamcorderProfile);
        mMediaRecorder.setOutputFile(getDir() + "/Mdm_video.mp4");
        mMediaRecorder.setPreviewDisplay(mSurfaceHolder.getSurface());