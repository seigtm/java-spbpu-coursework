import java.util.Objects;

public final class Hero {

    private MovementStrategy movementStrategy;

    public Hero(MovementStrategy movementStrategy) throws NullPointerException {
        setMovementStrategy(movementStrategy);
    }

    public void setMovementStrategy(MovementStrategy movementStrategy) throws NullPointerException {
        this.movementStrategy = Objects.requireNonNull(movementStrategy, "Movement strategy cannot be null");
    }

    public String move() {
        return movementStrategy.move();
    }

}
