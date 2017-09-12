package com.lteii.asteroid3d.game;

import com.badlogic.gdx.utils.Array;
import com.lteii.asteroid3d.game.patterns.Road;
import com.lteii.asteroid3d.utils.Direction;
import com.lteii.asteroid3d.utils.VectorInt2;

import static com.lteii.asteroid3d.game.patterns.Road.FLOOR_SIZE;


class RoadBuilder {


    static class Move {
        private final Direction direction;
        private final int distance;
        Move(Direction direction, int distance) {
            this.direction = direction;
            this.distance = distance;
        }
    }


    final Array<Road.FloorConstructor> floorConstructors = new Array<Road.FloorConstructor>();
    final Array<Road.BarrierConstructor> barrierConstructors = new Array<Road.BarrierConstructor>();

    RoadBuilder(Move...moves) {
        final Array<VectorInt2> filledPlaces = new Array<VectorInt2>();

        int posZ = 0;
        int posX = 0;

        for (int moveI=0; moveI<moves.length; moveI++) {
            final Move move = moves[moveI];
            final VectorInt2 directionVec = move.direction.getVector();
            for (int i=0; i<move.distance; i++) {
                // Check if not filled
                for (VectorInt2 filledPlace : filledPlaces)
                    if (filledPlace.equals(posZ, posX))
                        throw new IllegalStateException();
                filledPlaces.add(new VectorInt2(posZ, posX));

                floorConstructors.add(new Road.FloorConstructor(FLOOR_SIZE*posX, FLOOR_SIZE*posZ));

                if (i > 0 || moveI == 0) {
                    barrierConstructors.add(new Road.BarrierConstructor(move.direction, FLOOR_SIZE*posX, FLOOR_SIZE*posZ, 0, -5));
                    barrierConstructors.add(new Road.BarrierConstructor(move.direction, FLOOR_SIZE*posX, FLOOR_SIZE*posZ, 0, 5));
                } else {
                    final Direction lastRelativeDir = Direction.relativeDirection(moves[moveI-1].direction, move.direction);
                    if (lastRelativeDir.isUp()) {
                        barrierConstructors.add(new Road.BarrierConstructor(move.direction, FLOOR_SIZE*posX, FLOOR_SIZE*posZ, 0, -5));
                        barrierConstructors.add(new Road.BarrierConstructor(move.direction, FLOOR_SIZE*posX, FLOOR_SIZE*posZ, 0, 5));
                    } else if (lastRelativeDir.isLeft()) {
                        barrierConstructors.add(new Road.BarrierConstructor(move.direction, FLOOR_SIZE*posX, FLOOR_SIZE*posZ, 0, -5));
                        barrierConstructors.add(new Road.BarrierConstructor(moves[moveI-1].direction, FLOOR_SIZE*posX, FLOOR_SIZE*posZ, 0, -5));
                    } else if (lastRelativeDir.isRight()) {
                        barrierConstructors.add(new Road.BarrierConstructor(move.direction, FLOOR_SIZE*posX, FLOOR_SIZE*posZ, 0, 5));
                        barrierConstructors.add(new Road.BarrierConstructor(moves[moveI-1].direction, FLOOR_SIZE*posX, FLOOR_SIZE*posZ, 0, 5));
                    } else {
                        throw new IllegalStateException();
                    }
                }


                posX += directionVec.y;
                posZ += directionVec.x;

            }
        }
    }

}
