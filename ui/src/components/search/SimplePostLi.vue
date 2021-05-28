<template>
    <li class="simple-post">
        <div class="title" @click="$router.push(`/post/${item.pId}`)">{{item.title}}</div>
        <div class="content" @click="$router.push(`/post/${item.pId}`)">{{item.description}}</div>
        <div>
            <span class="author" @click="$router.push(`/user/${item.author.uid}`)">{{item.author.username}}</span>
            <span class="time">{{getTime(item.time)}}</span>
        </div>
    </li>
</template>

<script>
    import {dateFormat} from "../../global/clazz";

    export default {
        name: "SimplePostLi",
        props:['item'],
        methods:{
            getTime(date) {
                return dateFormat("YYYY-mm-dd HH:MM", date);
            }
        }
    }
</script>

<style scoped>
    .simple-post{
        list-style: none;
        background-color: #fce7e7;
        border-bottom: 1px solid #ffd0d0;
        height: 80px;
        padding-left: 20px;
        border-radius: 2px;
        margin-left: 10px;
    }
    .title{
        font-size: 20px;
        font-weight: bold;
    }
    .content{
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 1;
        -webkit-box-orient: vertical;
        word-break: break-all;
        height: 20px;
        margin-bottom: 10px;
    }
    .author{
        color: #228b22;
        font-size: 18px;
    }
    .author:hover{
        cursor: pointer;
        text-decoration: underline;
    }
    .time{
        color: #ada5a5;
        font-size: 16px;
        margin-left: 10px
    }
    .title:hover,.content:hover{
        text-decoration: underline;
        color: #2d2c68;
        cursor: pointer;
    }
</style>