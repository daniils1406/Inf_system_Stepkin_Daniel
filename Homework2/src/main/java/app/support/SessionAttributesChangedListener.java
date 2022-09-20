package app.support;

import jakarta.servlet.annotation.WebListener;
import jakarta.servlet.http.HttpSession;
import jakarta.servlet.http.HttpSessionAttributeListener;
import jakarta.servlet.http.HttpSessionBindingEvent;

import java.util.*;
import java.util.stream.Collectors;

import static java.util.Objects.nonNull;

@WebListener
public class SessionAttributesChangedListener implements HttpSessionAttributeListener {

    private static final Map<String, List<String>> SESSION_HISTORY = new HashMap<>();

    public static List<String> getSessionHistory(List<User> sessions, List<Room> chats, String idOfRoom,HttpSession session) {
        List<String> history = new LinkedList<>();
        if(!SESSION_HISTORY.isEmpty()){
            for(Room t:chats){
                for(User s : sessions){
                    if(s.id.equals(idOfRoom)){
                        if(SESSION_HISTORY.get(s.session)!=null){
                                if(t.getIdOfRoom().equals(s.id)){
                                    if(session.getId().equals(s.session))
                                    t.getMeseges().add(s.session+": "+SESSION_HISTORY.get(s.session).get(SESSION_HISTORY.get(s.session).size()-1));
                                }
                        }
                    }
                }
            }

        }

        if (nonNull(history)) {
            return new LinkedList<>(history);
        }
        return Collections.emptyList();
    }



//    public static List<Messege> getSessionHistory(List<User> sessions, String idOfRoom,List<Messege> messeges) {
//        List<Messege> hist=new LinkedList<>();
//        if(!SESSION_HISTORY.isEmpty()){
//            for(User s: sessions){
//                if(s.id.equals(idOfRoom)){
//                    if(SESSION_HISTORY.get(s.session)!=null){
//                        for(String w : SESSION_HISTORY.get(s.session)){
//                            for(Messege a: messeges){
//                                if(a.numberOfRoom.equals(idOfRoom) && a.messege.equals(w)){
//                                    a.setUserId(s.session);
//                                    hist.add(a);
//                                    break;
//                                }
//                            }
//
//                        }
//                    }
//                }else{
//                    if(SESSION_HISTORY.get(s.session)!=null){
//                        for(String w : SESSION_HISTORY.get(s.session)){
//                            for(Messege a: messeges){
//                                if(a.numberOfRoom.equals(idOfRoom) && a.messege.equals(w)){
//                                    hist.add(a);
//                                    break;
//                                }
//                            }
//
//                        }
//                    }
//                }
//            }
//        }
//        if (nonNull(hist)) {
//            return new LinkedList<>(hist);
//        }
//        return Collections.emptyList();
//    }


    @Override
    public void attributeAdded(HttpSessionBindingEvent event) {
        if(!event.getName().equals("NameOfRoom")){
            String message = formPredefinedHistoryMessage("ADDED", event.getName(), (String) event.getValue());
            putValueToHistory(event.getSession().getId(), message);
        }
    }

    @Override
    public void attributeRemoved(HttpSessionBindingEvent event) {
        if(!event.getName().equals("NameOfRoom")){
            String message = formPredefinedHistoryMessage("REMOVED", event.getName(), (String) event.getValue());
            putValueToHistory(event.getSession().getId(), message);
        }
    }

    @Override
    public void attributeReplaced(HttpSessionBindingEvent event) {
        if(!event.getName().equals("NameOfRoom")){
            String message = formPredefinedHistoryMessage("REPLACED", event.getName(),
                    (String) event.getSession().getAttribute(event.getName()));
            putValueToHistory(event.getSession().getId(), message);
        }

    }

    private void putValueToHistory(String sessionId, String message) {
        SESSION_HISTORY.computeIfAbsent(sessionId, key -> new LinkedList<>()).add(message);
    }


    private String formPredefinedHistoryMessage(String operationPrefix, String name, String value) {
        return String.format("%s",value);
    }


}