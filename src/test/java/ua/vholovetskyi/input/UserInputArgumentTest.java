package ua.vholovetskyi.input;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.Arguments;
import org.junit.jupiter.params.provider.MethodSource;

import java.util.stream.Stream;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertThrows;

class UserInputArgumentTest {

    @ParameterizedTest
    @MethodSource("correctInputArgs")
    void should_build_correct_user_input(String firstArg, String secondArg) {
        //given
        String[] args = new String[]{firstArg, secondArg};

        //when
        UserInputArgument input = new UserInputArgument(args);

        //then
        assertThat(input.getAttribute()).isNotBlank();
        assertThat(input.getFiles()).isNotEmpty();
        assertThat(input.getStatusParams()).isEmpty();
    }

    @ParameterizedTest
    @MethodSource("correctInputArgsWithTwoParams")
    void should_build_correct_user_input_with_two_params(String firstParam, String secondParam) {
        //given
        String[] args = new String[]{"order_test", "status", firstParam, secondParam};

        //when
        UserInputArgument input = new UserInputArgument(args);

        //then
        assertThat(input.getAttribute()).isNotBlank();
        assertThat(input.getStatusParams().size()).isEqualTo(2);
    }

    @ParameterizedTest
    @MethodSource("unsupportedParamsForItem")
    void should_throw_exception_if_given_params_for_attribute_item(String firstParam, String secondParam) {
        //given
        String[] input = new String[]{"order_test", "item", firstParam, secondParam};

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new UserInputArgument(input));
    }

    @ParameterizedTest
    @MethodSource("unsupportedParamsForOrderDate")
    void should_throw_exception_if_given_params_for_attribute_orderDate(String firstParam, String secondParam) {
        //given
        String[] input = new String[]{"order_test", "orderDate", firstParam, secondParam};

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new UserInputArgument(input));
    }
    @ParameterizedTest
    @MethodSource("inputIsBlank")
    void should_throw_exception_if_params_is_blank(String firstParam, String secondParam) {
        //given
        String[] input = new String[]{"order_test", "status", firstParam, secondParam};

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new UserInputArgument(input));
    }


    @Test
    void should_throw_exception_if_folder_does_not_exist() {
        //given
        String[] input = new String[]{"test2", "item"};

        //when
        //then
        assertThrows(IllegalArgumentException.class, () -> new UserInputArgument(input));
    }

    @ParameterizedTest
    @MethodSource("inputIsBlank")
    void should_throw_exception_for_blank_args(String firstArg, String secondArg) {
        //given
        String[] input = new String[]{firstArg, secondArg};

        //when

        //then
        assertThrows(IllegalArgumentException.class, () -> new UserInputArgument(input));
    }

    private static Stream<Arguments> inputIsBlank() {
        return Stream.of(
                Arguments.of(null, null),
                Arguments.of("", ""),
                Arguments.of("  ", " "),
                Arguments.of("\t", "\n")
        );
    }

    private static Stream<Arguments> correctInputArgs() {
        return Stream.of(
                Arguments.of("order_test", "item"),
                Arguments.of("order_test", "status"),
                Arguments.of("order_test", "orderDate")
        );
    }

    private static Stream<Arguments> correctInputArgsWithTwoParams() {
        return Stream.of(
                Arguments.of("new", "paid"),
                Arguments.of("new", "canceled"),
                Arguments.of("canceled", "shipped")
        );
    }

    private static Stream<Arguments> unsupportedParamsForItem() {
        return Stream.of(
                Arguments.of("Java 8 in Action", "Java Database"),
                Arguments.of("Design Patterns", "Java Concurrency in practice")
        );
    }

    private static Stream<Arguments> unsupportedParamsForOrderDate() {
        return Stream.of(
                Arguments.of("2024-01-25", "2024-01-26"),
                Arguments.of("2024-02-05", "2024-02-06")
        );
    }
}