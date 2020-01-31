package com.example.myott35

import android.media.MediaPlayer
import android.net.Uri
import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.widget.VideoView
import kotlinx.android.synthetic.main.activity_main.*


class MainActivity : AppCompatActivity() {
    var counter=0
    private var animationMode = true
    lateinit var otts: ArrayList<Ott>
    private var videoBG: VideoView? = null
    var mMediaPlayer: MediaPlayer? = null
    var mCurrentVideoPosition = 0
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)



        setDimension()

        videoBackgroundPreperation()
        // mainLayout.setBackgroundResource(R.drawable.paper)

        /*  button.setOnClickListener {
              if (imageView.visibility==View.VISIBLE) imageView.visibility=View.GONE
                      else imageView.visibility=View.VISIBLE
          }*/

        /* mainLayout.setOnClickListener {
             CoroutineScope(Dispatchers.Main).launch {
                 //delay(1000)
                 createOtt()
                 drawAllOtts()
                 lastApizode()
             }
         }*/
    }

    private fun setDimension() { // Adjust the size of the video
// so it fits on the screen
        val videoProportion = getVideoProportion()
        val screenWidth = resources.displayMetrics.widthPixels
        val screenHeight = resources.displayMetrics.heightPixels
        val screenProportion =
            screenHeight.toFloat() / screenWidth.toFloat()
        val lp = videoView.layoutParams
        if (videoProportion < screenProportion) {
            lp.height = screenHeight
            lp.width = (screenHeight.toFloat() / videoProportion).toInt()
            lp.height=lp.height*3
            lp.width=lp.width*3
        } else {
            lp.width = screenWidth
            lp.height = (screenWidth.toFloat() * videoProportion).toInt()
        }
        videoView.layoutParams = lp
    }

    // This method gets the proportion of the video that you want to display.
// I already know this ratio since my video is hardcoded, you can get the
// height and width of your video and appropriately generate  the proportion
//    as :height/width
    private fun getVideoProportion(): Float {
        return 1.5f
    }

    private fun videoBackgroundPreperation(){
        videoBG = findViewById(R.id.videoView) as VideoView
        val uri = Uri.parse(
            ("android.resource://" // First start with this,
                    + packageName // then retrieve your package name,
                    ) + "/" // add a slash,
                    + R.raw.myott30
        ) // and then finally add your video resource. Make sure it is stored
        // in the raw folder.
// Set the new Uri to our VideoView
        videoBG!!.setVideoURI(uri)
        // Start the VideoView
        videoBG!!.start()
        // Set an OnPreparedListener for our VideoView. For more information about VideoViews,
// check out the Android Docs: https://developer.android.com/reference/android/widget/VideoView.html
        videoBG!!.setOnPreparedListener { mediaPlayer ->
            mMediaPlayer = mediaPlayer
            // We want our video to play over and over so we set looping to true.
            mMediaPlayer!!.isLooping = true
            // We then seek to the current posistion if it has been set and play the video.
            if (mCurrentVideoPosition != 0) {
                mMediaPlayer!!.seekTo(mCurrentVideoPosition)
                mMediaPlayer!!.start()
            }
        }
    }

    /*override fun onPause() {
        super.onPause()
        // Capture the current video position and pause the video.
        mCurrentVideoPosition = mMediaPlayer!!.currentPosition
        videoBG!!.pause()
    }*/

    override fun onResume() {
        super.onResume()
        // Restart the video when resuming the Activity
        videoBG!!.start()
    }

    override fun onDestroy() {
        super.onDestroy()
        // When the Activity is destroyed, release our MediaPlayer and set it to null.
        mMediaPlayer!!.release()
        mMediaPlayer = null
    }
}
