package main;

import java.util.LinkedList;
import java.util.Queue;


public class RequestQueue {
    private Queue<Request> requests;

    public RequestQueue() {
        this.requests = new LinkedList<>();
    }

    public void addRequest(Request request) {
        requests.add(request);
    }

   
    public Request peekNextRequest() {
        return requests.peek();
    }

 
    public Request pollNextRequest() {
        return requests.poll();
    }

    public boolean hasRequests() {
        return !requests.isEmpty();
    }

    public int size() {
        return requests.size();
    }

    public void clear() {
        requests.clear();
    }
}
