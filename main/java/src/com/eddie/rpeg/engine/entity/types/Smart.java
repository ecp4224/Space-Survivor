package com.eddie.rpeg.engine.entity.types;

import com.eddie.rpeg.engine.entity.Entity;

/**
 * This interface declares an object to be "smart". This means that an entity may
 * have an AI or the entity is a player.
 *
 */
public interface Smart {
    /**
     * Alert this entity that the entity "cause" provided in the parameter did something.
     * @param cause
     *             The entity doing something.
     */
    public void alert(Entity cause);
}
