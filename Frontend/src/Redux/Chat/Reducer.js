import { createChat } from "./Action"
import { CREATE_CHAT, CREATE_GROUP, GET_USERS_CHAT } from "./ActionType"

const initialValue = {
    chats: [],
    createdGroup : null,
    createChat: null
}

export const chatReducer = (store=initialValue, {type, payload}) => {

    if(type===CREATE_CHAT){
        return { ...store, createChat: payload}
    }
    else if (type === CREATE_GROUP){
        return {...store, createdGroup:payload}
    }
    else if (type === GET_USERS_CHAT ){
        return {...store, chats:payload};
    }
    else{
        return store;
    }
}

