package com.adaptionsoft.games.uglytrivia;

import java.util.LinkedList;

public class Question {
    LinkedList<String> popQuestions;
    LinkedList<String> scienceQuestions;
    LinkedList<String> sportsQuestions;
    LinkedList<String> rockQuestions;

    public Question() {
        popQuestions = new LinkedList<String>();
        scienceQuestions = new LinkedList<String>();
        sportsQuestions = new LinkedList<String>();
        rockQuestions = new LinkedList<String>();
    }

    void initialize() {
        for (int i = 0; i < 50; i++) {
            popQuestions.addLast("Pop Question " + i);
            scienceQuestions.addLast(("Science Question " + i));
            sportsQuestions.addLast(("Sports Question " + i));
            rockQuestions.addLast("Rock Question " + i);
        }
    }

    public String getNextQuestion(String category) {
        if (category == "Pop")
            return popQuestions.removeFirst();
        if (category == "Science")
            return scienceQuestions.removeFirst();
        if (category == "Sports")
            return sportsQuestions.removeFirst();
        if (category == "Rock")
            return rockQuestions.removeFirst();
        return "";
    }
}
