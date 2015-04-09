package game_engine;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.Arrays;

import javafx.scene.image.Image;
import javafx.scene.image.ImageView;
import javafx.scene.image.PixelReader;
import javafx.scene.paint.Color;
import game_engine.sprite.Player;
import game_engine.sprite.Sprite;
/**
 * Class checks for collision occurrences between sprites
 * @author Michael Lee
 *
 */

public class CollisionEngine {
	
	/**
	 * 
	 * @param spriteA
	 * @param spriteB
	 * Checks for collision, executes behavior for collision
	 * PPC does not work if the imageview sets a fitwidth/height; resizing image within the imageview
	 *
	 */
	//TODO Implement collision class
	//TODO allow for resizing images
	public void executeCollision(ImageView spriteA, ImageView spriteB){
		//change parameters back to sprites
		
		boolean[][] bitMapA = createBitMap(spriteA.getImage());
		boolean[][] bitMapB = createBitMap(spriteB.getImage());

		ImageView s1 = spriteA;
		double aLeft = s1.getX();
		double aTop = s1.getY();
		double aRight = s1.getX()+s1.getImage().getWidth();
		double aBot = s1.getY()+s1.getImage().getHeight();
		
		ImageView s2 = spriteB;
		double bLeft = s2.getX();
		double bTop = s2.getY();
		double bRight = s2.getX()+s2.getImage().getWidth();
		double bBot = s2.getY()+s2.getImage().getHeight();
		
		double highLeft;
		double lowRight;
		double highTop;
		double lowBot;
		
		if(aLeft > bLeft)
			highLeft = aLeft;
		else
			highLeft = bLeft;
		if(aTop > bTop)
			highTop = aTop;
		else
			highTop = bTop;
		if(aRight > bRight)
			lowRight = bRight;
		else
			lowRight = aRight;
		if(aBot > bBot)
			lowBot = bBot;
		else
			lowBot = aBot;
		CollisionBox collisionBox = new CollisionBox(highLeft,highTop,lowRight,lowBot);

		int startY = (int) (highTop - aTop);
		int endY = (int) (lowBot - aTop);
		int startX = (int) (highLeft - aLeft);
		int endX = (int) (lowRight - aLeft);
		boolean[][] mapA = spliceBitMap(bitMapA, startY, endY, startX, endX); 
		int startY2 = (int) (highTop - bTop);
		int endY2 = (int) (lowBot - bTop);
		int startX2 = (int) (highLeft - bLeft);
		int endX2 = (int) (lowRight - bLeft);
		boolean[][] mapB = spliceBitMap(bitMapB, startY2, endY2, startX2, endX2); 
		
		boolean[][] collisionMap = isColliding(mapA,mapB);
		System.out.println(Arrays.deepToString(collisionMap));
		
//		
//	
//		
		
		
		
	}
	
	public static void main (String[] args){
		CollisionEngine engine = new CollisionEngine();
		 InputStream inputStream = null;
		try {
			inputStream = new FileInputStream("/Users/bluedevilmike/Documents/workspace/"
			 		+ "voogasalad_HighScrollers/src/game_engine/images/lightbulb.png");
		} catch (FileNotFoundException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		Image bulb=new Image(inputStream);
		boolean[][] bitmap = engine.createBitMap(bulb);
		
		ImageView s1 = new ImageView(bulb);
		ImageView s2 = new ImageView(bulb);
		s1.setX(-5);
		s1.setY(-5);
		s2.setX(15);
		s2.setY(15);
		
		engine.executeCollision(s1,s2);
		
		
		//position for 1st image centered (0,0) size 32x32
//		int aLeft = -15;
//		int aTop =-15;
//		int aRight =17 ;
//		int aBot = 17;
//		int bLeft = 15;
//		int bTop = 15;
//		int bRight = 47;
//		int bBot = 47;
//		//position for 2nd image centered (31,31) size 32x32
//		int highLeft;
//		int lowRight;
//		int highTop;
//		int lowBot;
//		if(aLeft > bLeft)
//			highLeft = aLeft;
//		else
//			highLeft = bLeft;
//		if(aTop > bTop)
//			highTop = aTop;
//		else
//			highTop = bTop;
//		if(aRight > bRight)
//			lowRight = bRight;
//		else
//			lowRight = aRight;
//		if(aBot > bBot)
//			lowBot = bBot;
//		else
//			lowBot = aBot;
//		System.out.println(highLeft);
//		System.out.println(lowRight);
//		System.out.println(highTop);
//		System.out.println(lowBot);
//		boolean[][] map = new boolean[lowBot-highTop][lowRight - highLeft];
//		//spriteA loop
//		int startY = highTop - aTop;
//		int endY = lowBot - aTop;
//		int startX = highLeft - aLeft;
//		int endX = lowRight - aLeft;
////		System.out.println(startY);
////		System.out.println(endY);
////		System.out.println(startX);
////		System.out.println(endX);
////		int startY = highTop - bTop;
////		int endY = lowBot - bTop;
////		int startX = highLeft - bLeft;
////		int endX = lowRight - bLeft;
//		System.out.println(startY);
//		System.out.println(endY);
//		System.out.println(startX);
//		System.out.println(endX);

	}
	//two sprites with positions
	//figure out collision box -> return to physics engine
	//get relevant portion of bitmap 
	private boolean[][] isColliding(boolean[][] mapA, boolean[][] mapB){
		boolean[][] bitMap = new boolean[mapA.length][mapA[0].length];
		for(int i = 0; i < mapA.length; i++)
			for(int j = 0; j < mapA[i].length; j++)
				bitMap[i][j] = mapA[i][j]&&mapB[i][j];
		
		return bitMap;
		}
	
	
	/*
	 * obtains intersecting pixelMaps of images
	 */
	private boolean[][] spliceBitMap(boolean[][] bitMap, int startX, int endX, int startY, int endY){
		
		boolean[][]bitSplice = new boolean[endY-startY][endX-startX];
		for(int i = 0; i < endY-startY; i++){
			for(int j = 0; j < endX - startX; j++){
				bitSplice[i][j] = bitMap[i+startY][j+startX];
				
			}
		}
		return bitSplice;
	}
	
	private boolean[][] createBitMap(Image src){
		PixelReader reader = src.getPixelReader();
		int width = (int)src.getWidth();
		int height = (int)src.getHeight();
		boolean[][] bitMap = new boolean[height][width];
		for(int y = 0; y < height; y++)
			for(int x = 0; x < width; x ++){
				bitMap[y][x] = reader.getArgb(x, y) != 0;
			}
		return bitMap;
	}
}
