public sealed interface MovementStrategy
        permits WalkingStrategy, HorseRidingStrategy, FlyingStrategy {

    public String move();

}
