package org.example;

public class Route {
    private int friendlyLocationCount, enemyLocationCount, neutralLocationCount;

    public Route() {
        neutralLocationCount = 0;
        friendlyLocationCount = 0;
        enemyLocationCount = 0;
    }

    public int getFriendlyLocationCount() {
        return friendlyLocationCount;
    }

    public int getEnemyLocationCount() {
        return enemyLocationCount;
    }

    public int getNeutralLocationCount() {
        return neutralLocationCount;
    }

    @Override
    public String toString() {
        return "Route{" +
                "friendlyLocationCount=" + friendlyLocationCount +
                ", enemyLocationCount=" + enemyLocationCount +
                ", neutralLocationCount=" + neutralLocationCount +
                '}';
    }

    public void addLocation(LocationType locationType)
    {
        switch (locationType)
        {
            case friendly -> {
                friendlyLocationCount++;
                break;
            }
            case enemy -> {
                enemyLocationCount++;
                break;
            }
            case neutral -> {
                neutralLocationCount++;
                break;
            }
        }
    }
}
