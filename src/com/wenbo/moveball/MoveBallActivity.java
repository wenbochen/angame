package com.wenbo.moveball;

import javax.microedition.khronos.opengles.GL10;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.AlphaModifier;
import org.andengine.entity.modifier.DelayModifier;
import org.andengine.entity.modifier.IEntityModifier.IEntityModifierListener;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.LoopEntityModifier.ILoopEntityModifierListener;
import org.andengine.entity.modifier.ParallelEntityModifier;
import org.andengine.entity.modifier.RotationByModifier;
import org.andengine.entity.modifier.RotationModifier;
import org.andengine.entity.modifier.ScaleModifier;
import org.andengine.entity.modifier.SequenceEntityModifier;
import org.andengine.entity.primitive.Rectangle;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.IModifier;
import org.andengine.util.modifier.LoopModifier;

import android.util.Log;

public class MoveBallActivity extends BaseGameActivity {
	private static final int CAMERA_WIDTH = 1920;
	private static final int CAMERA_HEIGHT = 1080; 

	private Camera mCamera;  
	private Scene mScene;  
	private RepeatingSpriteBackground background;  
	private TiledTextureRegion mFaceTextureRegion,headtr; 

/**
 * 创建一个引擎对象
 */
	@Override
	public EngineOptions onCreateEngineOptions() {
		 mCamera = new Camera(0,0,CAMERA_WIDTH,CAMERA_HEIGHT);  
		 //全屏显示
		 //引擎参数设置
		 EngineOptions mEngineOptions = new EngineOptions(true, ScreenOrientation.LANDSCAPE_SENSOR, 
				 new RatioResolutionPolicy(CAMERA_WIDTH, CAMERA_HEIGHT), mCamera);  
		 
		return mEngineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		//设置背景图片 和屏幕大小一样的图片 横屏
		this.background = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, 
				getTextureManager(), AssetBitmapTextureAtlasSource.create(  
						 this.getAssets(), "backgroundy.jpg"), 
						 getVertexBufferObjectManager());   
		 BitmapTextureAtlas mTexture = new BitmapTextureAtlas(getTextureManager(),64,32,TextureOptions.BILINEAR_PREMULTIPLYALPHA);  
		 mFaceTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTexture, this, "face.png", 0, 0,2,1); //第一个笑脸 
		 headtr = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTexture, this, "face.png", 0, 0,2,1);  //第二个
		 /** 
		  * 参数说明: 
		  * mTexure是在内存中放置贴图资源用的,64,32是图片要求的宽和高,必须是2的n次方大小.如:2,4,8,16,32,64,128,512,1024.... 
		  * 并且要比原图的宽高要大 
		  *  
		  * mFaceTextureRegion相当于从mTexure中扣图,因为mTexure是由很多图集组成的,要从中截取一片出来 
		  * 0,0代表截图的top,right坐标(起点坐标),2和1分别代表贴图中一张存在2列1行 
		  *  
		  */  
		 mTexture.load();  
		 pOnCreateResourcesCallback.onCreateResourcesFinished();  

	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		//创建场景 并设置场景
		 mScene = new Scene();  
		 mScene.setBackground(background);  
		 final float centerX = (CAMERA_WIDTH - mFaceTextureRegion.getWidth()) / 2;//计算贴图的中心坐标  
		 final float centerY = (CAMERA_HEIGHT - mFaceTextureRegion.getHeight()) / 2;  
		 final Ball mBall = new Ball(centerX, centerY,128, 128,this.mFaceTextureRegion,getVertexBufferObjectManager());  
		 //把精灵添加到场景中
		 mScene.attachChild(mBall);  
		 //添加点击屏幕事件
		 mScene.setOnSceneTouchListener(mBall);
		 
		 //创建一个矩形设置颜色为红色
		 final Rectangle rect = new Rectangle(centerX+100,centerY,64,64,getVertexBufferObjectManager());
		rect.setColor(1, 0, 0);
		//创建一个动画精灵
		final AnimatedSprite face = new AnimatedSprite(centerX - 100, centerY, headtr, getVertexBufferObjectManager());
		face.setBlendFunction(GL10.GL_SRC_ALPHA, GL10.GL_ONE_MINUS_SRC_ALPHA);
		// 创建完精灵后,就给精灵添加修改器了,这个地方是比较灵活的,完全根据自己的需要来制作.
		//LoopEntityModifier是一个循环修改器,第三个参数就是循环的次数,-1代表无限重复,从源代码中可以看出各个参数的作用
		  final LoopEntityModifier shapeModifier = 
					new LoopEntityModifier(new IEntityModifierListener(){

						@Override
						public void onModifierStarted(IModifier<IEntity> pModifier,
								IEntity pItem) {
							
						}

						@Override
						public void onModifierFinished(
								IModifier<IEntity> pModifier, IEntity pItem) {
							
						}

						
					},
					-1, //无限循环
					new ILoopEntityModifierListener(){

						@Override
						public void onLoopStarted(
								LoopModifier<IEntity> pLoopModifier, int pLoop,
								int pLoopCount) {
							
						}

						@Override
						public void onLoopFinished(
								LoopModifier<IEntity> pLoopModifier, int pLoop,
								int pLoopCount) {
						
						}
					
					}, 
	               new SequenceEntityModifier(new RotationModifier(1, 0, 90),
							new AlphaModifier(5, 1, 0),
							new AlphaModifier(1, 0, 1),
							new ScaleModifier(2, 1, 0.5f),
							new DelayModifier(0.5f),
							new ParallelEntityModifier(
									new ScaleModifier(3, 0.5f, 5),
									new RotationByModifier(3, 90)
							),
							new ParallelEntityModifier(
									new ScaleModifier(3, 5, 1),
									new RotationModifier(3, 180, 0)){
						
						
					}));
		 /* 效果解释:两个精灵在5秒的时间内慢慢变为透明
		  在1秒时间内变为完全不透明
		  尺寸在2秒内变为0.5倍大小
		  暂停等待0.5秒
		  在三秒内完成由0.5倍大小到5倍大小渐变,同时旋转90度
		  在三秒内由5被大小变为一倍大小,同时由180度转回0度*/
		  //修改器绑定到精灵上
		  face.registerEntityModifier(shapeModifier);
		  rect.registerEntityModifier(shapeModifier.deepCopy());
		mScene.attachChild(face);
		mScene.attachChild(rect);
		 //画100条直线
//		 for(int i=0;i<100;i++){
//			 float x1 = (float) (Math.random()*CAMERA_WIDTH);
//			 float x2 = (float) (Math.random()*CAMERA_WIDTH);
//			 float y1 = (float) (Math.random()*CAMERA_HEIGHT);
//			 float y2 = (float) (Math.random()*CAMERA_HEIGHT);
//			 //创建直线
//			 Line line = new Line(x1, y1, x2, y2,null);
//			 line.setColor(Color.GREEN);
//			 mScene.attachChild(line);
//		 }
		 pOnCreateSceneCallback.onCreateSceneFinished(mScene);  

		
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		
		 pOnPopulateSceneCallback.onPopulateSceneFinished();  
		
	}




}
