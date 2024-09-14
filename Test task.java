package com.example.demo;



import java.util.ArrayList;
import java.util.List;
import java.util.stream.Stream;


//Надо написать программу, в которой 6 тредов: Chandler, Joey, Monica, Phoebe, Rachel и Ross разыгрывают в
//консоли сценки из ситкома. Каждый печатает свое имя и реплику.

public class DemoApplication {


	public record Phrase(String author, String text) {
	}

	public static class DialogueTask implements Runnable {

		public Integer currentIndexInDialogue;

		public Integer dialogueSize;

		List<Phrase> dialogue;

		public DialogueTask(List<Phrase> dialogue) {
			this.dialogue = dialogue;
			this.currentIndexInDialogue = 0;
			this.dialogueSize = dialogue.size();
		}

		@Override
		public synchronized void run() {

			while (currentIndexInDialogue < dialogueSize) {

				while (currentIndexInDialogue < dialogueSize && !dialogue
								.get(currentIndexInDialogue)
								.author()
								.equals(Thread.currentThread().getName())) {

					try {
						wait();
					} catch (InterruptedException e) {
						throw new RuntimeException(e);
					}
				}


				if (currentIndexInDialogue < dialogueSize) {
					System.out.println(dialogue.get(currentIndexInDialogue));
					currentIndexInDialogue++;
					notifyAll();
				}
			}

		}

	}

	public static void main(String[] args) {
		List<Phrase> dialogue = new ArrayList<>(List.of(
				new Phrase("Joey", "Hey, hey."),
				new Phrase("Chandler", "Hey."),
				new Phrase("Phoebe", "Hey."),
				new Phrase("Chandler", "And this from the cry-for-help department. Are you wearing makeup?"),
				new Phrase("Joey", "Yes, I am. As of today, I am officially Joey Tribbiani, actor slash model."),
				new Phrase("Chandler", "That's so funny, 'cause I was thinking you look more like Joey Tribbiani, man slash woman."),
				new Phrase("Joey", "You know those posters for the City Free Clinic?"),
				new Phrase("Phoebe", "What were you modeling for?"),
				new Phrase("Monica", "Oh, wow, so you're gonna be one of those \"healthy, healthy, healthy guys\"?"),
				new Phrase("Phoebe", "You know, the asthma guy was really cute."),
				new Phrase("Chandler", "Do you know which one you're gonna be?"),
				new Phrase("Joey", "No, but I hear lyme disease is open, so... (crosses fingers)"),
				new Phrase("Chandler", "Good luck, man. I hope you get it."),
				new Phrase("Joey", "Thanks.")
		));

		Runnable dialogueTask = new DialogueTask(dialogue);

		Stream.of(
						new Thread(dialogueTask, "Joey"),
						new Thread(dialogueTask, "Chandler"),
						new Thread(dialogueTask, "Phoebe"),
						new Thread(dialogueTask, "Monica"),
						new Thread(dialogueTask, "Rachel"),
						new Thread(dialogueTask, "Ross"))
				.forEach(Thread::start);
	}



}

