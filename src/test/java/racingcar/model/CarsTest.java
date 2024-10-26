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

class CarsTest {
    private final NumberGenerator randomGenerator = new RandomNumberGenerator();
    private final NumberGenerator nineGenerator = FixedNumberGenerator.nineGenerator();
    private final NumberGenerator zeroGenerator = FixedNumberGenerator.zeroGenerator();
    private final List<String> validCarNames = List.of(
            "car1",
            "car2",
            "car3"
    );

    @Test
    void 자동차들_생성_성공() {
        assertDoesNotThrow(() ->
                Cars.of(randomGenerator, validCarNames)
        );
    }

    @Test
    void 자동차들_생성_실패() {
        List<String> duplicateCarNames = List.of("car", "car");

        assertThatThrownBy(() ->
                Cars.of(randomGenerator, duplicateCarNames)
        ).isInstanceOf(IllegalArgumentException.class);
    }

    @Test
    void 움직이기_성공_상태_검증() {
        Cars cars = Cars.of(nineGenerator, validCarNames);
        Map<String, Integer> expect = Map.of(
                validCarNames.get(0), 1,
                validCarNames.get(1), 1,
                validCarNames.get(2), 1
        );

        cars.moveAll();

        assertThat(cars.status()).isEqualTo(expect);
    }

    @Test
    void findWinners() {
        Cars cars = Cars.of(nineGenerator, validCarNames);
        List<Car> expect = new ArrayList<>(validCarNames
                .stream()
                .map(carName -> Car.of(nineGenerator, carName))
                .toList());
        expect.forEach(Car::tryMove);

        cars.moveAll();
        List<Car> winners = cars.findWinners();

        assertThat(winners).usingRecursiveComparison().
                ignoringFields("numberGenerator").isEqualTo(expect);
    }
}