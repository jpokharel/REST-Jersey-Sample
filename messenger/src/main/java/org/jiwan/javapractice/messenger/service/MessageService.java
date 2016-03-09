package org.jiwan.javapractice.messenger.service;

import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;
import java.util.Map;

import org.jiwan.javapractice.messenger.database.DatabaseClass;
import org.jiwan.javapractice.messenger.exception.DataNotFoundException;
import org.jiwan.javapractice.messenger.model.Message;

public class MessageService {

	private Map<Long, Message> messages = DatabaseClass.getMessages();

	public MessageService() {
		messages.put(1L, new Message(1L, "New first Message", "Jiwan Bindu"));
		messages.put(2L, new Message(2L, "New second Message", "Krishna Tulsa"));
		messages.put(3L, new Message(3L, "New last Message", "Jyoti Pradip"));
	}

	public List<Message> getAllMessages() {
		return new ArrayList<Message>(messages.values());
	}

	public Message getMessage(long id) {
		Message message = messages.get(id);
		if (message == null) {
			throw new DataNotFoundException("Message with id " + id + " not Found");
		}
		return message;
	}

	public List<Message> getAllMessagesByYear(int year) {
		List<Message> messagesForYear = new ArrayList<>();
		Calendar cal = Calendar.getInstance();
		for (Message message : messages.values()) {
			cal.setTime(message.getCreated());
			if (cal.get(Calendar.YEAR) == year)
				messagesForYear.add(message);
		}
		return messagesForYear;
	}

	public List<Message> getAllMessagesPaginated(int start, int size) {
		ArrayList<Message> list = new ArrayList<Message>(messages.size());
		if (start + size > list.size())
			return new ArrayList<Message>();
		return list.subList(start, start + size);
	}

	public Message addMessage(Message message) {
		message.setId(messages.size() + 1);
		messages.put(message.getId(), message);
		return message;
	}

	public Message updateMessage(Message message) {
		if (message.getId() <= 0) {
			return null;
		}
		messages.put(message.getId(), message);
		return message;
	}

	public Message removeMessage(long id) {
		return messages.remove(id);
	}
}
