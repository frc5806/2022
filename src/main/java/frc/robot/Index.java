package frc.robot;
final class Index implements RobotData{
    private Hopper hop;
    private Feeder feed;
    private boolean fed = false;
    public Index(Hopper hop, Feeder feed){
        this.hop = hop;
        this.feed = feed;
    }
    public void toShoot(){
        hop.spinUp(1);
        feed.spinUp(1);
    }
    public void stop(){
        hop.spinDown();;
        feed.spinDown();
    }
    public boolean mCOne(){
        if(feed.intakeM1.getOutputCurrent()>0){
            hop.spinUp(-.25);
            feed.spinUp(.5);
            fed = true;
            return false;
        }
        else if(feed.intakeM1.getOutputCurrent()<0&&fed){
            fed = false;
            return true;}
        else{
            hop.spinUp(.5);
            feed.spinUp(.5);
            return false;
        }
    }
    public void clear(){
        hop.spinUp(-1);
        feed.spinUp(-1);
    }
}