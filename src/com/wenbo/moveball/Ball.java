package com.wenbo.moveball;

import org.andengine.entity.scene.IOnSceneTouchListener;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.input.touch.TouchEvent;
import org.andengine.opengl.texture.region.ITiledTextureRegion;
import org.andengine.opengl.vbo.VertexBufferObjectManager;

import android.util.Log;
/**
 * 这里的球是一个精灵类
 * @Description 
 * @author <a href="http://my.oschina.net/chenbo">chenbo</a>
 * @date 2016年1月28日 下午2:30:37
 * @version V1.0
 */
public  class Ball  extends AnimatedSprite implements IOnSceneTouchListener{
	
	private static final int CAMERA_WIDTH = 1920;
	private static final int CAMERA_HEIGHT = 1080; 
	private final static float BALL_VELOCITY = 150f;//球的移动速度 
		float mVelocityX = BALL_VELOCITY;//球的x方向速度
		float mVelocityY = BALL_VELOCITY ;//球的y方向速度
		
		private static final int MAX_VELOCITY_X = 300; 
		private static final int MAX_VELOCITY_Y = 300; 
		private static final int MAX_ACCELERATION_X = 20; //x方向加速度
		private static final int MAX_ACCELERATION_Y = 20; //y方向加速度
		private static final int DEFAULT_VELOCITY = 10; 

		public Ball(float pX, float pY, float pWidth, float pHeight,
				ITiledTextureRegion pTiledTextureRegion,
				VertexBufferObjectManager pVertexBufferObjectManager) {
			super(pX, pY, pWidth, pHeight, pTiledTextureRegion, pVertexBufferObjectManager);
			// TODO Auto-generated constructor stub
			mX = 100;
			mY = 100;
		}

		@Override
		protected void onManagedUpdate(float pSecondsElapsed) {
			// TODO Auto-generated method stub
			
				if(this.mX < 0) {
					setVelocityX(BALL_VELOCITY);
				} else if(  this.mX + this.getWidth() > CAMERA_WIDTH){
					setVelocityX(-BALL_VELOCITY);

				}

				if(this.mY < 0 ) {
					setVelocityY(BALL_VELOCITY);
				} else if(this.mY + this.getHeight() > CAMERA_HEIGHT){
					setVelocityY(-BALL_VELOCITY);
				}
				
			

				mX += mVelocityX * pSecondsElapsed;
				mY += mVelocityY * pSecondsElapsed;
				
				
				this.setPosition(mX, mY);
		//	Log.d("Season",pSecondsElapsed + "");
			
			super.onManagedUpdate(pSecondsElapsed);
			
			
		}
		
		void setVelocityX(float vx){
			
			mVelocityX = vx;
		}
		
		
		void setVelocityY(float vy){
		    mVelocityY = vy;
		}
/**
 * 点击屏幕时的事件处理
 * @param pScene
 * @param pSceneTouchEvent
 * @return
 */
		@Override
		public boolean onSceneTouchEvent(Scene pScene,
				TouchEvent pSceneTouchEvent) {
			//触摸位置
			float touchX = pSceneTouchEvent.getX(); 
			float touchY = pSceneTouchEvent.getY(); 
			WenboLog.info(touchX+","+touchY);
			return true;
		}
		
		
		public void move(float px,float py){
			
		}
		
	}
    

