package de.marekparucha.newtowerdefense.actor.enemies;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * Created by Marek on 20.07.2017.
 */

public class Movement {

    private final static Integer[] STARTTILE = {1,129,255,383,317,445,314,443,344};
    private final static Integer[] UPDOWNTILE = {5,37,420,452,355,387,419,451};
    private final static Integer[] RIGHTLEFTTILE = {8,134,166,198,230,391,423,455};
    private final static Integer[] CHANGETILE = {4,218,284,289,313,325,378,457};
    private final static Integer[] ENDTILE = {97,223,283,285,212,351,411,413,448};

    private final static int DOWN = 1;
    private final static int RIGHT = 2;
    private final static int UP = 3;
    private final static int LEFT = 4;
    private final static int CHANGE_DOWN = 5;
    private final static int CHANGE_RIGHT = 6;
    private final static int CHANGE_UP = 7;
    private final static int CHANGE_LEFT = 8;
    private final static int CHANGE_2ROADS = 9;
    private final static int END = 10;

    private int x,y;

    private TiledMapTileLayer layer;
    private static List<Integer> movements;

    private static double speed = 1;

    private static int[] startCoordinate;

    public Movement()
    {
        startCoordinate = new int[2];
    }

    public void setMovements(TiledMapTileLayer layer)
    {
        this.layer = layer;
        TiledMapTileLayer.Cell cell;
        x = 0;
        y = 0;
        boolean finished = false;
        for(int i = layer.getWidth()-1; i >= 0; i--)
        {
            for(int j = layer.getHeight()-1; j >= 0; j--)
            {
                cell = layer.getCell(i,j);
                if(cell!= null)
                {
                    int id = cell.getTile().getId();
                    if(Arrays.asList(STARTTILE).contains(id))
                    {
                        startCoordinate[0] = i*cell.getTile().getTextureRegion().getRegionWidth();
                        startCoordinate[1] = j*cell.getTile().getTextureRegion().getRegionHeight();
                        x = i;
                        y = j;
                        finished = true;
                    }

                }

                if(finished) break;
            }
            if(finished) break;
        }
        finished = false;

        boolean changed = false;
        boolean started = true;
        ArrayList<Integer> way = new ArrayList<Integer>();
        List<Integer> nextCells = null;
        List<Integer> currentWay;
        while(!finished)
        {
            if(!changed)
            {
                if(nextCells == null) nextCells = findNextCells(null);
                currentWay = findWayTillCross(nextCells.get(0),nextCells.get(1),started);
                way.addAll(currentWay);
                if(way.get(way.size()-3) == END) finished = true;
                else
                {
                    x = way.get(way.size()-2);
                    y = way.get(way.size()-1);
                    changed = true;
                }
                way.remove(way.size()-1);
                way.remove(way.size()-1);
                started = false;
            }
            else
            {
                nextCells = findNextCells(new Integer[]{way.get(way.size()-2)});
                currentWay = findWayTillCross(nextCells.get(0),nextCells.get(1),false);
                currentWay.remove(currentWay.size()-1);
                currentWay.remove(currentWay.size()-1);
                currentWay.remove(currentWay.size()-1);
                way.add(currentWay.size()*-1);
                way.addAll(currentWay);
                currentWay = findWayTillCross(nextCells.get(2),nextCells.get(3),false);
                x = currentWay.get(currentWay.size()-2);
                y = currentWay.get(currentWay.size()-1);
                currentWay.remove(currentWay.size()-1);
                currentWay.remove(currentWay.size()-1);
                currentWay.remove(currentWay.size()-1);
                nextCells = findNextCells(new Integer[]{way.get(way.size()-1), currentWay.get(currentWay.size()-1)});
                way.add(currentWay.size()*-1);
                way.addAll(currentWay);

                if(way.get(way.size()-3) == END) finished = true;
                else changed = false;

            }
        }

        movements = way;
        Gdx.app.log("Movement", "created Movement successfully");
    }

    private List<Integer> findWayTillCross(int nextX, int nextY, boolean start)
    {
        ArrayList<Integer> way = new ArrayList<Integer>();
        boolean changed = true;
        boolean finished = false;
        int direction = 0;
        int x = nextX;
        int y = nextY;
        TiledMapTileLayer.Cell cell;
        while(!finished)
        {
            cell = layer.getCell(x,y);
            int id = cell.getTile().getId();

            if(direction==0)
            {
                if(start)
                {
                    if(this.y > nextY) way.add(DOWN);
                    else if(this.y < nextY) way.add(UP);
                    else if(this.x > nextX) way.add(LEFT);
                    else if(this.x < nextX) way.add(RIGHT);
                }
                else
                {
                    if(this.y > nextY) way.add(CHANGE_DOWN);
                    else if(this.y < nextY) way.add(CHANGE_UP);
                    else if(this.x > nextX) way.add(CHANGE_LEFT);
                    else if(this.x < nextX) way.add(CHANGE_RIGHT);
                }

            }

            if(Arrays.asList(UPDOWNTILE).contains(id))
            {
                if(changed)
                {
                    if(Arrays.asList(CHANGETILE).contains(layer.getCell(x,y-1).getTile().getId()))
                    {
                        y+=1;
                        direction = UP;
                        way.add(UP);

                    }
                    else
                    {
                        y-=1;
                        direction = DOWN;
                        way.add(DOWN);
                    }
                    changed = false;
                }
                else if(direction == UP)
                {
                    y+=1;
                    way.add(UP);
                }
                else
                {
                    y-=1;
                    way.add(DOWN);
                }
            }
            else if(Arrays.asList(RIGHTLEFTTILE).contains(id))
            {
                if(changed)
                {
                    if(Arrays.asList(CHANGETILE).contains(layer.getCell(x-1,y).getTile().getId()))
                    {
                        x+=1;
                        direction = RIGHT;
                        way.add(RIGHT);

                    }
                    else
                    {
                        x-=1;
                        direction = LEFT;
                        way.add(LEFT);
                    }
                    changed = false;
                }
                else if(direction == RIGHT)
                {
                    x+=1;
                    way.add(RIGHT);
                }
                else
                {
                    x-=1;
                    way.add(LEFT);
                }
            }
            else if (Arrays.asList(CHANGETILE).contains(id))
            {
                ArrayList<Integer> ways = new ArrayList<Integer>();
                if(layer.getCell(x,y+1)!=null) ways.add(UP);
                if(layer.getCell(x,y-1)!=null) ways.add(DOWN);
                if(layer.getCell(x+1,y)!=null) ways.add(RIGHT);
                if(layer.getCell(x-1,y)!=null) ways.add(LEFT);
                if(ways.size() > 2)
                {
                    finished = true;
                    way.add(CHANGE_2ROADS);
                    way.add(x);
                    way.add(y);
                }
                else
                {
                    if(direction == UP || direction == DOWN)
                    {
                        if(ways.contains(RIGHT))
                        {
                            x+=1;
                            direction = RIGHT;
                            way.add(CHANGE_RIGHT);
                        }
                        else
                        {
                            x-=1;
                            direction = LEFT;
                            way.add(CHANGE_LEFT);
                        }
                    }
                    else
                    {
                        if(ways.contains(DOWN))
                        {
                            y-=1;
                            direction = DOWN;
                            way.add(CHANGE_DOWN);
                        }
                        else
                        {
                            y+=1;
                            direction = UP;
                            way.add(CHANGE_UP);
                        }
                    }
                    changed = true;
                }
            }
            else if(Arrays.asList(ENDTILE).contains(id))
            {
                finished = true;
                way.add(END);
                way.add(x);
                way.add(y);
            }
        }
        return way;
    }

    private List<Integer> findNextCells(Integer[] direction)
    {
        ArrayList<Integer> list = new ArrayList<Integer>();
        if(direction == null)
        {
            if(layer.getCell(x,y+1)!=null)
            {
                list.add(x);
                list.add(y+1);
            }
            else if(layer.getCell(x,y-1)!=null)
            {
                list.add(x);
                list.add(y-1);
            }
            else if(layer.getCell(x+1,y)!=null)
            {
                list.add(x+1);
                list.add(y);
            }
            else if(layer.getCell(x-1,y)!=null)
            {
                list.add(x-1);
                list.add(y);
            }
            return list;
        }
        else if(direction.length ==1)
        {
            if(direction[0] == RIGHT)
            {
                if(layer.getCell(x,y+1)!=null)
                {
                    list.add(x);
                    list.add(y+1);
                }
                if(layer.getCell(x,y-1)!=null)
                {
                    list.add(x);
                    list.add(y-1);
                }
                if(layer.getCell(x-1,y)!=null)
                {
                    list.add(x+1);
                    list.add(y);
                }
                return list;
            }
            else if(direction[0] == LEFT)
            {
                if(layer.getCell(x,y+1)!=null)
                {
                    list.add(x);
                    list.add(y+1);
                }
                if(layer.getCell(x,y-1)!=null)
                {
                    list.add(x);
                    list.add(y-1);
                }
                if(layer.getCell(x+1,y)!=null)
                {
                    list.add(x-1);
                    list.add(y);
                }
                return list;
            }
            else if(direction[0] == DOWN)
            {
                if(layer.getCell(x,y-1)!=null)
                {
                    list.add(x);
                    list.add(y+1);
                }
                if(layer.getCell(x+1,y)!=null)
                {
                    list.add(x+1);
                    list.add(y);
                }
                if(layer.getCell(x-1,y)!=null)
                {
                    list.add(x-1);
                    list.add(y);
                }
                return list;
            }
            else
            {
                if(layer.getCell(x,y+1)!=null)
                {
                    list.add(x);
                    list.add(y-1);
                }
                if(layer.getCell(x+1,y)!=null)
                {
                    list.add(x+1);
                    list.add(y);
                }
                if(layer.getCell(x-1,y)!=null)
                {
                    list.add(x-1);
                    list.add(y);
                }
                return list;
            }
        }
        else
        {
                if(Arrays.asList(direction).contains(DOWN) && layer.getCell(x,y-1)!=null)
                {
                    list.add(x);
                    list.add(y-1);
                }
                else if(Arrays.asList(direction).contains(UP)&&layer.getCell(x,y+1)!=null)
                {
                    list.add(x);
                    list.add(y+1);
                }
                else if(Arrays.asList(direction).contains(LEFT) && layer.getCell(x-1,y)!=null)
                {
                    list.add(x-1);
                    list.add(y);
                }
                else if(Arrays.asList(direction).contains(RIGHT)&& layer.getCell(x+1,y)!=null)
                {
                    list.add(x+1);
                    list.add(y);
                }
                else
                {
                    list.add(x);
                    list.add(y);
                }
            return list;
        }

    }
}
