package com.driver;

import java.util.*;

import org.springframework.stereotype.Repository;

@Repository
public class WhatsappRepository {

    //Assume that each user belongs to at most one group
    //You can use the below mentioned hashmaps or delete these and create your own.
    private HashMap<Group, List<User>> groupUserMap;
    private HashMap<Group, List<Message>> groupMessageMap;
    private HashMap<Message, User> senderMap;
    private HashMap<Group, User> adminMap;
    private HashSet<String> userMobile;
    private int customGroupCount;
    private int messageId;

    public WhatsappRepository(){
        this.groupMessageMap = new HashMap<Group, List<Message>>();
        this.groupUserMap = new HashMap<Group, List<User>>();
        this.senderMap = new HashMap<Message, User>();
        this.adminMap = new HashMap<Group, User>();
        this.userMobile = new HashSet<>();
        this.customGroupCount = 0;
        this.messageId = 0;
    }

    public String createUser(String name, String mobile) throws Exception {
        //If the mobile number exists in database, throw "User already exists" exception
        //Otherwise, create the user and return "SUCCESS"
        if(userMobile.contains(mobile)) throw new Exception("User already exists");
        userMobile.add(mobile);
        return "SUCCESS";
    }


    public Group createGroup(List<User> users){
        int s = users.size();
        Group grp = new Group();
        if(s==2)
             grp = new Group(users.get(1).getName(),users.size());
        else if(s>2)
            grp = new Group("Group "+customGroupCount,users.size());


        customGroupCount++;
        User admin = new User(users.get(0).getName(),users.get(0).getMobile());
        adminMap.put(grp,admin);
        groupUserMap.put(grp,users);
        return grp;
    }

    public int createMessage(String content){
        // The 'i^th' created message has message id 'i'.
        // Return the message id.
        messageId++;
        Message msg = new Message(messageId,content);

        return messageId;
    }

    public int sendMessage(Message message, User sender, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "You are not allowed to send message" if the sender is not a member of the group
        //If the message is sent successfully, return the final number of messages in that group.

        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        if(!groupUserMap.get(group).contains(sender)) throw new Exception("You are not allowed to send message");

        List<Message> list = groupMessageMap.getOrDefault(group,new ArrayList<>());
        list.add(message);
        groupMessageMap.put(group,list);
        senderMap.put(message,sender);

        return list.size();
    }


    public String changeAdmin(User approver, User user, Group group) throws Exception{
        //Throw "Group does not exist" if the mentioned group does not exist
        //Throw "Approver does not have rights" if the approver is not the current admin of the group
        //Throw "User is not a participant" if the user is not a part of the group
        //Change the admin of the group to "user" and return "SUCCESS". Note that at one time there is only one admin and the
        // admin rights are transferred from approver to user.
        if(!groupUserMap.containsKey(group)) throw new Exception("Group does not exist");
        if(!adminMap.get(group).getName().equals(approver.getName())) throw new Exception("Approver does not have rights");
        if(!groupUserMap.get(group).contains(user)) throw new Exception("User is not a participant");

        adminMap.put(group,user);

        return "SUCCESS";
    }

}
