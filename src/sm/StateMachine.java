package sm;

import java.util.*;

public class StateMachine {

    private static final int START = 0;
    private static final int SIGN = 1;
    private static final int DIGITS = 2;

    private final Set<Integer> endStates;
    private final Map<Integer, Map<Character, Integer>> transitions = new HashMap<>();

    public StateMachine(int startState, Set<Integer> endStates) {
        this.endStates = endStates;
    }

    public void add(Integer from, Character ch, Integer to) {
        Map<Character, Integer> fromMap = transitions.computeIfAbsent(from, k -> new HashMap<>());
        fromMap.put(ch, to);
    }

    public void add(Integer from, List<Character> chars, Integer to) {
        for (Character ch : chars) {
            add(from, ch.charValue(), to);
        }
    }

    public Integer getNext(Integer state, Character ch) {
        Map<Character, Integer> stateTransitions = transitions.get(state);
        return stateTransitions.get(ch);
    }

    public int match(String str, int from) {
        int state = START;
        int i = from;
        while (i < str.length()) {
            char ch = str.charAt(i);
            Integer nextState = getNext(state, ch);
            if (nextState == null)
                break;
            state = nextState;
            i++;
        }
        if (endStates.contains(state)) {
            return i;
        } else {
            return -1;
        }
    }

    public void findAll(String str) {
        int i = 0;
        while (i < str.length()) {
            int match = match(str, i);
            if (match < 0) {
                i++;
            }
            else {
                System.out.println(str.substring(i, match));
                i = match;
            }
        }
    }



    public static void main(String[] args) {
        List<Character> signs = Arrays.asList('+', '-');
        List<Character> digits = Arrays.asList('0', '1', '2', '3','4','5','6','7','8','9');

        Set<Integer> endStates = Collections.singleton(DIGITS);
        StateMachine sm = new StateMachine(START, endStates);

        sm.add(START, signs, SIGN);
        sm.add(SIGN, digits, DIGITS);
        sm.add(DIGITS, digits, DIGITS);

        String str;
        System.out.println("Enter your string: ");
        Scanner in = new Scanner(System.in);
        str = in.nextLine();
        sm.findAll(str);
        in.close();
    }

}
