package org.isep.rottencave.score.processors;

import java.util.List;

import org.isep.rottencave.score.RemoteScore;

public interface ScoreListProcessor {
	public void processList(List<RemoteScore> scoresList);
}
