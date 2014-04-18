package com.me.rvbgame;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.scenes.scene2d.Action;
import com.badlogic.gdx.scenes.scene2d.actions.Actions;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.utils.Align;
import com.badlogic.gdx.scenes.scene2d.utils.TextureRegionDrawable;
import com.badlogic.gdx.utils.Scaling;

public class ActionAnimator extends Table {

	private Image leftUnitImage;
	private Image rightUnitImage;
	
	private Image leftAnimImage;
	private Image rightAnimImage;
	
	private Label leftDamageLabel;
	private Label rightDamageLabel;
	
	private Table containerTable;
	private Table leftContainerTable;
	private Table centerContainerTable;
	private Table rightContainerTable;
	
	static final int ICON_NATIVE_SIZE = 64;
	static final int WINDOW_NATIVE_WIDTH = (int)(RvBWorld.WORLD_NATIVE_RES.x * 0.5f);
	static final int WINDOW_NATIVE_HEIGHT = (int)(RvBWorld.WORLD_NATIVE_RES.y * 0.5f);
	private float moveByValue = ICON_NATIVE_SIZE / 2;
//	private float leftIcoLastPosX;
//	private float rightIcoLastPosX;
//	private float leftIcoLastPosY;
//	private float rightIcoLastPosY;
	
	private boolean bDoDamage;
	private boolean bRightAtack;
	
	private boolean bPlayAnim;
	
	private boolean notifyEndofAnim = false;
	private RvBPlayer notifyEndofAnimTo = null;
	
	public ActionAnimator() {
		super();
		
		setColor(1, 1, 1, 0);
	}

	public ActionAnimator(Skin skin) {
		super(skin);
		
		setColor(1, 1, 1, 0);
	}

/*	private void InitImages() {
		
	}*/
	
	public void show() {
		
	}
	
	public void dispose() {
		
	}
	
	public void resize(int width, int height) {
		setWidth(width);
		setHeight(height);
		
		float heightDelta = RvBBase.battleScreen.screenResH / RvBWorld.WORLD_NATIVE_RES.y;
		float icoSize = ICON_NATIVE_SIZE * heightDelta;
		float windowWidth = WINDOW_NATIVE_WIDTH * heightDelta;
		float windowHeight = WINDOW_NATIVE_HEIGHT * heightDelta;
		moveByValue = icoSize / 2;
				
		leftUnitImage = applyTexture("data/ap_icon.png", false);
		rightUnitImage = applyTexture("data/ap_icon.png", false);
		
		leftAnimImage = applyTexture("data/ap_icon.png", false);
		rightAnimImage = applyTexture("data/ap_icon.png", false);
		
		leftDamageLabel = new Label("-100", RvBBase.battleScreen.getSkin());
		leftDamageLabel.setColor(1, 0, 0, 0);
		rightDamageLabel = new Label("-100", RvBBase.battleScreen.getSkin());
		rightDamageLabel.setColor(1, 0, 0, 0);
		
		containerTable = new Table(RvBBase.battleScreen.getSkin());
		containerTable.setWidth(windowWidth);
		containerTable.setHeight(windowHeight);
		containerTable.center();
		containerTable.setBackground("textfield");
//		containerTable.setColor(1, 1, 1, 0.5f);
		add(containerTable).width(windowWidth * 1.5f).height(windowHeight * 0.6f);
		
		leftContainerTable = new Table(RvBBase.battleScreen.getSkin());
//		leftContainerTable.setWidth(64);
//		leftContainerTable.setHeight(64);
		centerContainerTable = new Table(RvBBase.battleScreen.getSkin());
		rightContainerTable = new Table(RvBBase.battleScreen.getSkin());
		
		containerTable.add();
		containerTable.add(new Label("Attack!", RvBBase.battleScreen.getSkin()));
		containerTable.row();
		
		containerTable.add(leftContainerTable);
		containerTable.add(centerContainerTable);
		containerTable.add(rightContainerTable);
		
		leftContainerTable.add(leftDamageLabel);
		leftContainerTable.add(leftUnitImage).width(icoSize).height(icoSize);
		
		centerContainerTable.add(leftAnimImage).width(icoSize).height(icoSize);
		centerContainerTable.add(rightAnimImage).width(icoSize).height(icoSize);
		
		rightContainerTable.add(rightUnitImage).width(icoSize).height(icoSize);
		rightContainerTable.add(rightDamageLabel);
		
//		leftUnitImage = applyTexture("data/atacker_melee.png");
//		Gdx.app.log("BVGE", "ActionAnimator:resize() "+leftUnitImage.getDrawable());
//		((TextureRegionDrawable)leftUnitImage.getDrawable()).getRegion().setRegion(new Texture("data/atacker_melee.png"));
		
//		leftIcoLastPosX = leftAnimImage.getX();
//		rightIcoLastPosX = rightAnimImage.getX();
//		leftIcoLastPosY = leftAnimImage.getY();
//		rightIcoLastPosY = rightAnimImage.getY();
	}
	
    public Image applyTexture(String path, boolean invertX){

        Texture avaTexture = new Texture(Gdx.files.internal(path));
        avaTexture.setFilter(Texture.TextureFilter.Linear, Texture.TextureFilter.Linear);
        TextureRegion region;
        if (invertX)
        {
        	region = new TextureRegion(avaTexture, 512, 0, 0, 512);
        } else
        {
        	region = new TextureRegion(avaTexture, 0, 0, 512, 512);
        }
        Image avaImage = new Image(region);
        avaImage.setScaling(Scaling.stretch);
        avaImage.setAlign((Align.bottom | Align.left));
//        avaImage.setSize(unit.getAvaSize().x, unit.getAvaSize().y);

        return avaImage;
    }

    public void changeTexture(Image image, String path) {
    	if (image != null && !path.isEmpty())
    	{
    		((TextureRegionDrawable)image.getDrawable()).getRegion().setRegion(new Texture(Gdx.files.internal(path)));
    	}
	}
    
    public void showAndStartAnim(boolean isDoDamage, boolean isRightAtack, RvBPlayer notifyPlayer) {
    	if (bPlayAnim)
    	{
    		return;
    	}
    	
    	if (notifyPlayer != null)
    	{
    		notifyEndofAnim = true;
    		notifyEndofAnimTo = notifyPlayer;
    	}
    	
    	setVisible(true);
/*		this.addAction( Actions.sequence(Actions.fadeIn(0.5f), Actions.delay(1.0f), Actions.fadeOut(0.5f),
        	new Action() {
        		public boolean act( float delta ) {
        			setVisible(false);
        			return true;
        		}
        	}));*/
    	bPlayAnim = true;
    	bDoDamage = isDoDamage;
    	bRightAtack = isRightAtack;
    	fadeIn(0.5f);
	}
    
    private void fadeIn(float time) {
		this.addAction( Actions.sequence(Actions.fadeIn(time),
	        	new Action() {
	        		public boolean act( float delta ) {
	        			animationPlay(1.0f);
	        			return true;
	        		}
	        	}));
	}
    
    private void animationPlay(float time) {
		this.addAction( Actions.sequence(Actions.delay(time),
	        	new Action() {
	        		public boolean act( float delta ) {
	        			fadeOut(0.5f);
	        			return true;
	        		}
	        	}));
		leftAnimImage.addAction(Actions.moveBy(moveByValue, 0, 0.5f));
		rightAnimImage.addAction(Actions.sequence(Actions.moveBy(-moveByValue, 0, 0.5f),
	        new Action() {
	    		public boolean act( float delta ) {
	    			fadeInLabels(0.15f);
	    			return true;
	    		}
    		}));
	}
    
    private void fadeInLabels(float time) {
		if (bDoDamage)
		{
			if (bRightAtack)
			{
				leftDamageLabel.addAction(Actions.fadeIn(time));
			} else
			{
				rightDamageLabel.addAction(Actions.fadeIn(time));
			}
		}
	}
    
    private void fadeOut(float time) {
		this.addAction( Actions.sequence(Actions.fadeOut(time),
	        	new Action() {
	        		public boolean act( float delta ) {
	        			setVisible(false);
	        			leftAnimImage.addAction(Actions.moveBy(-moveByValue, 0, 0));
//	        			leftAnimImage.addAction(Actions.moveTo(leftIcoLastPosX, leftIcoLastPosY));
	        			rightAnimImage.addAction(Actions.moveBy(moveByValue, 0, 0));
//	        			rightAnimImage.addAction(Actions.moveTo(rightIcoLastPosX, rightIcoLastPosY));
	        			leftDamageLabel.setColor(1, 0, 0, 0);
	        			rightDamageLabel.setColor(1, 0, 0, 0);
	        			bPlayAnim = false;
	        			if (notifyEndofAnim)
	        			{
	        				if (notifyEndofAnimTo != null)
	        				{
	        					notifyEndofAnimTo.actionAnimEnded();
	        				}
	        			}
	        			return true;
	        		}
	        	}));
	}
    
	public Image getLeftUnitImage() {
		return leftUnitImage;
	}

	public Image getRightUnitImage() {
		return rightUnitImage;
	}

	public Image getLeftAnimImage() {
		return leftAnimImage;
	}

	public Image getRightAnimImage() {
		return rightAnimImage;
	}

	public Label getLeftDamageLabel() {
		return leftDamageLabel;
	}

	public Label getRightDamageLabel() {
		return rightDamageLabel;
	}
}
