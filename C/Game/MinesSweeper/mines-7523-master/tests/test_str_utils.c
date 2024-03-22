#include "greatest.h"
#include "../str_utils.h"
#include "../ui.h"
#include "../game.h"

TEST remove_spaces_from_name_without_spaces()
{
    char name_to_change[] = "peto";
    str_remove_spaces(name_to_change);
    ASSERT_STR_EQ("peto", name_to_change);
    PASS();
}

TEST remove_spaces_from_name_with_spaces()
{
    char name_to_change[] = "peto z \nruzinova\n\n";
    str_remove_spaces(name_to_change);
    ASSERT_STR_EQ("petozruzinova", name_to_change);
    PASS();
}

// Test repeat_game with input 'y'
TEST repeat_game_yes()
{
    bool input = true;
    ASSERT_EQ(repeat_game(input), true);
    PASS();
}

// Test repeat_game with input 'n'
TEST repeat_game_no()
{
    bool input = false;
    ASSERT_EQ(repeat_game(input), false);
    PASS();
}

// Test repeat_game with invalid input
TEST repeat_game_invalid_input()
{
    bool input = false;
    ASSERT_EQ(repeat_game(input), false);
    PASS();
}

TEST test_read_parameters_of_game_for_levels()
{
    char easy_input[] = "10 10 1\n";
    char medium_input[] = "15 15 2\n";
    char hard_input[] = "20 20 3\n";

    FILE *fp = fopen("tests/input.txt", "w");
    fclose(fp);

    // Test case 1: easy level
    freopen("tests/input.txt", "r", stdin);
    FILE *input_file = fopen("tests/input.txt", "w");
    fputs(easy_input, input_file);
    fclose(input_file);
    int *easy_output = read_parameters_of_game();
    ASSERT_EQ(10, easy_output[0]);
    ASSERT_EQ(10, easy_output[1]);
    ASSERT_EQ(20, easy_output[2]); // 10*10*0.2 = 20
    free(easy_output);

    // Test case 2: medium level
    freopen("tests/input.txt", "r", stdin);
    input_file = fopen("tests/input.txt", "w");
    fputs(medium_input, input_file);
    fclose(input_file);
    int *medium_output = read_parameters_of_game();
    ASSERT_EQ(15, medium_output[0]);
    ASSERT_EQ(15, medium_output[1]);
    ASSERT_EQ(79, medium_output[2]); // 15*15*0.35 = 78.75, rounded to 79
    free(medium_output);

    // Test case 3: hard level
    freopen("tests/input.txt", "r", stdin);
    input_file = fopen("tests/input.txt", "w");
    fputs(hard_input, input_file);
    fclose(input_file);
    int *hard_output = read_parameters_of_game();
    ASSERT_EQ(20, hard_output[0]);
    ASSERT_EQ(20, hard_output[1]);
    ASSERT_EQ(200, hard_output[2]); // 20*20*0.5 = 200
    free(hard_output);

    remove("tests/input.txt");
    PASS();
}

TEST test_read_parameters_of_game_for_extreme_values()
{
    char min_input[] = "2 2 1\n";
    char max_input[] = "30 30 3\n";

    // Test case 1: min values
    FILE *input_file = fopen("tests/input.txt", "w");
    freopen("tests/input.txt", "r", stdin);
    fputs(min_input, input_file);
    fclose(input_file);
    int *min_output = read_parameters_of_game();
    ASSERT_EQ(2, min_output[0]);
    ASSERT_EQ(2, min_output[1]);
    ASSERT_EQ(1, min_output[2]); // 2*2*0.2 = 0.8, rounded to 1
    free(min_output);

    // Test case 2: max values
    freopen("tests/input.txt", "r", stdin);
    input_file = fopen("tests/input.txt", "w");
    fputs(max_input, input_file);
    fclose(input_file);
    int *max_output = read_parameters_of_game();
    ASSERT_EQ(30, max_output[0]);
    ASSERT_EQ(30, max_output[1]);
    ASSERT_EQ(450, max_output[2]); // 30*30*0.5 = 450
    free(max_output);

    remove("tests/input.txt");
    PASS();
}

TEST test_read_parameters_of_game_for_invalid_data()
{
    char *test_inputs[] = {
        "1 1 1\n",      // values below minimum
        "32 32 2\n",    // values above maximum
        "10 10 text\n", // text instead of numbers
        "10 10 4\n",    // invalid difficulty level
    };
    int *result[4];

    // Loop for invalid data
    for (int i = 0; i < 4; i++)
    {
        FILE *input_file = fopen("tests/input.txt", "w");
        freopen("tests/input.txt", "r", stdin);
        fputs(test_inputs[i], input_file);
        fclose(input_file);
        result[i] = read_parameters_of_game();
    }

    for (int i = 0; i < 4; i++)
    {
        ASSERT_EQ(NULL, result[i]);
        free(result[i]);
    }
    remove("tests/input.txt");
    PASS();
}

SUITE(test_user_interface)
{
    RUN_TEST(remove_spaces_from_name_without_spaces);
    RUN_TEST(remove_spaces_from_name_with_spaces);
    RUN_TEST(repeat_game_yes);
    RUN_TEST(repeat_game_no);
    RUN_TEST(repeat_game_invalid_input);
    RUN_TEST(test_read_parameters_of_game_for_levels);
    RUN_TEST(test_read_parameters_of_game_for_extreme_values);
    RUN_TEST(test_read_parameters_of_game_for_invalid_data);
}