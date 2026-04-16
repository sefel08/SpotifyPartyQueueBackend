package org.sfl.spotifybackendnew.Objects.SmartQueue;

import org.sfl.spotifybackendnew.DTOs.Music.Track;
import org.sfl.spotifybackendnew.Objects.Party.PartyUser;

import java.util.*;
import java.util.concurrent.CopyOnWriteArrayList;
import java.util.concurrent.atomic.AtomicReference;

public class SmartQueue {

    private final Map<UUID, PartyUser> userMap;
    private final CopyOnWriteArrayList<UUID> joinOrder;

    private final AtomicReference<List<Track>> cachedQueue = new AtomicReference<>(new ArrayList<>());

    public SmartQueue(Map<UUID, PartyUser> userMap, CopyOnWriteArrayList<UUID> joinOrder) {
        this.userMap = userMap;
        this.joinOrder = joinOrder;
    }


    public List<Track> getQueue() {
        return cachedQueue.get();
    }
    public void refreshQueue() {
        List<Track> newQueue = new ArrayList<>();
        List<UUID> userIds = new ArrayList<>(List.copyOf(joinOrder));
        Map<UUID, Integer> addedUserTracksCount = new HashMap<>();

        // initialize added user tracks count
        for (UUID userId : userIds) {
            addedUserTracksCount.put(userId, 0);
        }

        while (!userIds.isEmpty()) {
            List<UUID> userIdsToRemove = new ArrayList<>();

            for (UUID userId : userIds) {
                int currentTrackId = addedUserTracksCount.get(userId);

                List<Track> userQueueRef = userMap.get(userId).getQueue();
                newQueue.add(userQueueRef.get(currentTrackId));

                if (userQueueRef.size() <= currentTrackId++)
                {
                    userIdsToRemove.add(userId);
                }
                else
                {
                    addedUserTracksCount.put(userId, currentTrackId++);
                }
            }

            userIds.removeAll(userIdsToRemove);
        }

        updateCache(newQueue);
    }

    private void updateCache(List<Track> newCalculatedQueue) {
        cachedQueue.set(List.copyOf(newCalculatedQueue));
    }
}
