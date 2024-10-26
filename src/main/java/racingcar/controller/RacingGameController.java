package racingcar.controller;

import java.util.List;
import racingcar.model.Racing;
import racingcar.util.NumberGenerator;
import racingcar.view.RacingGameView;

public class RacingGameController {
    private final NumberGenerator numberGenerator;
    private final RacingGameView racingGameView;

    public RacingGameController(NumberGenerator numberGenerator, RacingGameView racingGameView) {
        this.numberGenerator = numberGenerator;
        this.racingGameView = racingGameView;
    }

    public void startGame() {
        List<String> carNames = racingGameView.inputCarNames();
        int attempt = racingGameView.inputAttempt();
        Racing racing = Racing.of(numberGenerator, carNames, attempt);

        race(racing);

        List<String> winnersNames = racing.winnersNames();
        racingGameView.printWinner(winnersNames);
    }

    private void race(Racing racing) {
        racingGameView.printGameStartMessage();
        while (!racing.isFinish()) {
            racing.race();
            racingGameView.printRacingStatus(racing.status());
        }
    }
}
