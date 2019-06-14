package com.example.android.smartdispatcher;

import java.util.ArrayList;
import java.util.List;

public class removetsp  {
    private final int N;
    private final int START_NODE;
    private final int FINISHED_STATE;

    private double[][] distance;
    private double minTourCost = Double.POSITIVE_INFINITY;

    private List<Integer> tour = new ArrayList<>();
    private boolean ranSolver = false;

    public removetsp(double[][] distance) {
        this(0, distance);
    }

    public removetsp(int startNode, double[][] distance) {

        this.distance = distance;
        N = distance.length;
        START_NODE = startNode;
        FINISHED_STATE = (1 << N) - 1;
    }

    public List<Integer> getTour() {
        if (!ranSolver) solve();
        return tour;
    }

    public double getTourCost() {
        if (!ranSolver) solve();
        return minTourCost;
    }

    public void solve() {

        int state = 1 << START_NODE;
        Double[][] memo = new Double[N][1 << N];
        Integer[][] prev = new Integer[N][1 << N];
        minTourCost = tsp(START_NODE, state, memo, prev);

        int index = START_NODE;
        while (true) {
            tour.add(index);
            Integer nextIndex = prev[index][state];
            if (nextIndex == null) break;
            int nextState = state | (1 << nextIndex);
            state = nextState;
            index = nextIndex;
        }
        ranSolver = true;
    }

    private double tsp(int i, int state, Double[][] memo, Integer[][] prev) {

        if (state == FINISHED_STATE) return 0;

        if (memo[i][state] != null) return memo[i][state];

        double minCost = Double.POSITIVE_INFINITY;
        int index = -1;
        for (int next = 0; next < N; next++) {

            if ((state & (1 << next)) != 0) continue;

            int nextState = state | (1 << next);
            double newCost = distance[i][next] + tsp(next, nextState, memo, prev);
            if (newCost < minCost) {
                minCost = newCost;
                index = next;
            }
        }
        prev[i][state] = index;
        return memo[i][state] = minCost;
    }
}
