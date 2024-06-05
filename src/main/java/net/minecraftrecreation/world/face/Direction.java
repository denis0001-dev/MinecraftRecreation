package net.minecraftrecreation.world.face;

public enum Direction {
    NORTH,
    SOUTH,
    EAST,
    WEST,
    UP,
    DOWN,

    Direction() {};

    @Override
    public String toString() {
        return switch (this) {
            case NORTH -> "north";
            case SOUTH -> "south";
            case EAST -> "east";
            case WEST -> "west";
            case UP -> "up";
            case DOWN -> "down";
            default -> throw new InvalidDirectionException(this);
        };
    }

    public Direction opposite() {
        return switch (this) {
            case NORTH -> SOUTH;
            case SOUTH -> NORTH;
            case EAST -> WEST;
            case WEST -> EAST;
            case UP -> DOWN;
            case DOWN -> UP;
            default -> throw new InvalidDirectionException(this);
        };
    }

    public Direction left() {
        return switch (this) {
            case NORTH -> WEST;
            case SOUTH -> EAST;
            case EAST -> NORTH;
            case WEST -> SOUTH;
            default -> throw new InvalidDirectionException(this);
        };
    }

    public Direction right() {
        return switch (this) {
            case NORTH -> EAST;
            case SOUTH -> WEST;
            case EAST -> SOUTH;
            case WEST -> NORTH;
            default -> throw new InvalidDirectionException(this);
        };
    }
}
