package com.me.rvbgame;

public class RvBPlayer extends RvBBase{

    public RvBTower tower;
    public RvBUnit[] units;

    public boolean isAI;

    private RvBWorld world;

    public RvBPlayer(BattleScreen parentScreen, RvBWorld world) {
        super(parentScreen);
        this.world = world;
    }

    public void beginTurn()
    {
        if(isAI)
            this.makeMove();
        else {
//       enable interaction
        }
    }

    private void makeMove() {
//        make move by AI
        this.world.endTurn(this);
    }
}
