package racingcar.model;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.assertThatThrownBy;
import static org.junit.jupiter.api.Assertions.assertDoesNotThrow;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import org.junit.jupiter.api.Test;
import racingcar.util.FixedNumberGenerator;
import racingcar.util.NumberGenerator;
import racingcar.util.RandomNumberGenerator;

class RacingTest {
    private final NumberGenerator randomGenerator = new RandomNumberGenerator();
    private final List<String> validCarNames = List.of(
            "car1",
            "car2",
            "car3"
    );
    private final Cars moveCars = Cars.of(FixedNumberGenerator.nineGenerator(), validCarNames);
    private final Cars notMoveCars = Cars.of(FixedNumberGenerator.zeroGenerator(), validCarNames);
    private final Cars validCars = Cars.of(randomGenerator, validCarNames);
    private final int validAttempt = 5;

    @Test
    void 레이스_생성_성공() {
        assertDoesNotThrow(() ->
                Racing.of(validCars, validAttempt)
        );
    }

    @Test
    void 레이스_생성_실패_시도_횟수_오류() {
        int invalidAttempt = 0;

        assertThatThrownBy(() ->
                Racing.of(validCars, invalidAttempt)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 레이스_성공() {
        Racing racing = Racing.of(validCars, 1);

        assertDoesNotThrow(racing::makeAttempt);
    }

    @Test
    void 레이스_실패() {
        Racing racing = Racing.of(validCars, 1);
        racing.makeAttempt();

        assertThatThrownBy(racing::makeAttempt)
                .isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 레이스_시작_끝() {
        Racing racing = Racing.of(validCars, 1);

        assertThat(racing.isFinish()).isFalse();
        racing.makeAttempt();
        assertThat(racing.isFinish()).isTrue();
    }

    @Test
    void 상태_검증() {
        Racing racing = Racing.of(moveCars, 2);
        Map<String, Integer> expect = Map.of(
                validCarNames.get(0), 1,
                validCarNames.get(1), 1,
                validCarNames.get(2), 1
        );

        racing.makeAttempt();
        Map<String, Integer> status = racing.status();

        assertThat(status).isEqualTo(expect);
    }

    @Test
    void 우승자_이름() {
        Racing racing = Racing.of(moveCars, 2);
        List<String> expect = new ArrayList<>(validCarNames);

        racing.makeAttempt();
        List<String> winnersNames = racing.winnersNames();

        assertThat(winnersNames).isEqualTo(expect);
    }
}