package com.wenbo.moveball;

import org.andengine.engine.camera.Camera;
import org.andengine.engine.options.EngineOptions;
import org.andengine.engine.options.ScreenOrientation;
import org.andengine.engine.options.resolutionpolicy.RatioResolutionPolicy;
import org.andengine.entity.IEntity;
import org.andengine.entity.modifier.LoopEntityModifier;
import org.andengine.entity.modifier.PathModifier;
import org.andengine.entity.modifier.PathModifier.IPathModifierListener;
import org.andengine.entity.modifier.PathModifier.Path;
import org.andengine.entity.scene.Scene;
import org.andengine.entity.scene.background.RepeatingSpriteBackground;
import org.andengine.entity.sprite.AnimatedSprite;
import org.andengine.opengl.texture.TextureOptions;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlas;
import org.andengine.opengl.texture.atlas.bitmap.BitmapTextureAtlasTextureRegionFactory;
import org.andengine.opengl.texture.atlas.bitmap.source.AssetBitmapTextureAtlasSource;
import org.andengine.opengl.texture.region.TiledTextureRegion;
import org.andengine.ui.activity.BaseGameActivity;
import org.andengine.util.modifier.ease.EaseSineInOut;
/**
 * 创建一个重复背景
 * @Description 
 * @author <a href="http://my.oschina.net/chenbo">chenbo</a>
 * @date 2016年1月29日 上午11:13:10
 * @version V1.0
 */
public class PlayGroundActivity extends BaseGameActivity {
	  private static final int CAMERA_WIDTH=1920;
		private static final int CAMERA_HEIGHT=1080;
		private Camera mCamera;
		private RepeatingSpriteBackground mGrassBackground;
		private TiledTextureRegion mPlayerTextureRegion;
		private BitmapTextureAtlas mTexture;
		

	@Override
	public EngineOptions onCreateEngineOptions() {
		mCamera = new Camera(0, 0, CAMERA_WIDTH, CAMERA_HEIGHT);
		EngineOptions engineOptions = new EngineOptions(true,ScreenOrientation.LANDSCAPE_SENSOR, new RatioResolutionPolicy(CAMERA_WIDTH,CAMERA_HEIGHT), mCamera);
		return engineOptions;
	}

	@Override
	public void onCreateResources(
			OnCreateResourcesCallback pOnCreateResourcesCallback)
			throws Exception {
		this.mTexture = new BitmapTextureAtlas(getTextureManager(), 128, 128, TextureOptions.BILINEAR_PREMULTIPLYALPHA);
		// 本例中用到一个72x128大小,3列4行的精灵战士
		this.mPlayerTextureRegion = BitmapTextureAtlasTextureRegionFactory.createTiledFromAsset(mTexture, this,"player.png", 0,0,3, 4);
		this.mGrassBackground = new RepeatingSpriteBackground(CAMERA_WIDTH, CAMERA_HEIGHT, getTextureManager(), 
				AssetBitmapTextureAtlasSource.create(getAssets(), "green_bg.png"), getVertexBufferObjectManager());
		
		mTexture.load();
		pOnCreateResourcesCallback.onCreateResourcesFinished();
		
	}

	@Override
	public void onCreateScene(OnCreateSceneCallback pOnCreateSceneCallback)
			throws Exception {
		 final float centerX = (CAMERA_WIDTH - mPlayerTextureRegion.getWidth()) / 2;//计算贴图的中心坐标  
		 final float centerY = (CAMERA_HEIGHT - mPlayerTextureRegion.getHeight()) / 2;  
		 //精灵行走的路径 每个to都是一个目的地(这几个点之间走的都是直线)
		 final Path path = new Path(4)
		 		 .to(20, 20)
				 .to(20, CAMERA_HEIGHT - 138)
				 .to(CAMERA_WIDTH-100, CAMERA_HEIGHT - 138)
				 .to(CAMERA_WIDTH-100, 20).to(20, 20);

			Scene mScene = new Scene();
	        mScene.setBackground(mGrassBackground);
	        final AnimatedSprite  player = new AnimatedSprite(20, 20, 96, 128, mPlayerTextureRegion, getVertexBufferObjectManager());
	        
	        //修改路径
	        PathModifier pathModifier = new PathModifier(30.0f, path, new IPathModifierListener(){

				@Override
				public void onPathStarted(PathModifier pPathModifier,
						IEntity pEntity) {
					// TODO Auto-generated method stub
					
				}

				@Override
				public void onPathWaypointStarted(PathModifier pPathModifier,
						IEntity pEntity, int pWaypointIndex) {
					// TODO Auto-generated method stub
					switch(pWaypointIndex) {
					case 0:
						player.animate(new long[]{200, 200, 200}, 6, 8, true);
						break;
					case 1:
						player.animate(new long[]{200, 200, 200}, 3, 5, true);
						break;
					case 2:
						player.animate(new long[]{200, 200, 200}, 0, 2, true);
						break;
					case 3:
						player.animate(new long[]{200, 200, 200}, 9, 11, true);
						break;
				}
				}

				@Override
				public void onPathWaypointFinished(PathModifier pPathModifier,
						IEntity pEntity, int pWaypointIndex) {
					WenboLog.info(pWaypointIndex+"==走完了");
					
				}

				@Override
				public void onPathFinished(PathModifier pPathModifier,
						IEntity pEntity) {
					WenboLog.info("All==走完了");
					
				}

			
				
			},EaseSineInOut.getInstance());
	        
	        LoopEntityModifier loopModifier = new LoopEntityModifier(pathModifier, -1);
	        player.registerEntityModifier(loopModifier);
	        mScene.attachChild(player);	
	        pOnCreateSceneCallback.onCreateSceneFinished(mScene);
		
	}

	@Override
	public void onPopulateScene(Scene pScene,
			OnPopulateSceneCallback pOnPopulateSceneCallback) throws Exception {
		 pOnPopulateSceneCallback.onPopulateSceneFinished();  
		
	}

	
}
