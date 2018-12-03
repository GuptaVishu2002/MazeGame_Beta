import javafx.scene.image.Image;


public class MapData {
    public static final int TYPE_WALL   = 1;
    public static final int TYPE_NONE   = 0;
    public static final int TYPE_GOAL   = 3;
    public static final int TYPE_KEYS   = 2;
    public Image[] mapImage;
    private int[] map;
    private int[] map2image;
    private int width;
    private int height;
    
    MapData(int x, int y){
	mapImage = new Image[4];
	mapImage[TYPE_NONE] = new Image("SPACE.png");
	mapImage[TYPE_WALL] = new Image("WALL.png");
	mapImage[TYPE_GOAL] = new Image("GOAL.png");
	mapImage[TYPE_KEYS] = new Image("ITEM.png");
	width  = x;
	height = y;
	map = new int[y*x];
	fillMap(MapData.TYPE_WALL);
	digMap(1, 3);
	/* 追加 */
	setKey();
	setGoal();
    }

    public int getHeight(){
	return height;
    }

    public int getWidth(){
	return width;
    }

    public int toIndex(int x, int y){
	return x + y * width;
    }
    
    public int getMap(int x, int y) {
	if (x < 0 || width <= x || y < 0 || height <= y) {
	    return -1;
	}
	return map[toIndex(x,y)];
    }

    public Image getImage(int x, int y) {
	return mapImage[getMap(x,y)];
    }

    public void setMap(int x, int y, int type){
	if (x < 1 || width <= x-1 || y < 1 || height <= y-1) {
	    return;
	}
	map[toIndex(x,y)] = type;
    }
    
    public void fillMap(int type){
	for (int y=0; y<height; y++){
	    for (int x=0; x<width; x++){
		map[toIndex(x,y)] = type;
	    }
	}
    }

    public void digMap(int x, int y){
	setMap(x, y, MapData.TYPE_NONE);
	int[][] dl = {{0,1},{0,-1},{-1,0},{1,0}};
	int[] tmp;
	
	for (int i=0; i<dl.length; i++) {
	    int r = (int)(Math.random()*dl.length);
	    tmp = dl[i];
	    dl[i] = dl[r];
	    dl[r] = tmp;
	}

	for (int i=0; i<dl.length; i++){
	    int dx = dl[i][0];
	    int dy = dl[i][1];
	    if (getMap(x+dx*2, y+dy*2) == MapData.TYPE_WALL){
		setMap(x+dx, y+dy, MapData.TYPE_NONE);
		digMap(x+dx*2, y+dy*2);
	    } 
	    
	  }
	
    }
    
      /* 追加 */
    public void setKey(){
        int max = width * height;
        int count = 0;
        while(true){
            int keyPos = (int)(Math.random()*max);
            if(map[keyPos] == TYPE_NONE){
                map[keyPos] = TYPE_KEYS;
                count++;
            }
            if(count >= 3) break;
        }
    }
    
    public void setGoal(){
        int max = width * height;
        int count = 0;
        while(true){
            int GoalPos = (int)(Math.random()*max);
            if(map[GoalPos] == TYPE_NONE){
			setMap(19, 13, MapData.TYPE_GOAL);
                count++;
            }
            if(count >= 1) break;
        }
    }


      public void printMap(){
	for (int y=0; y<height; y++){
	    for (int x=0; x<width; x++){
		if (getMap(x,y) == MapData.TYPE_WALL){
		    System.out.print("++");
		}else{
		    System.out.print("  ");
		}
	    }
	    System.out.print("\n");
	}
    }
}

