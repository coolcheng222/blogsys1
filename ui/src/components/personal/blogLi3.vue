<template>
    <li class="blogtitle" @click="$router.push(`/post/${item.pId}`)">
        <div class="title">{{item.title}}</div>
        <div class="time">{{getTime(item.time)}}</div>
    </li>
</template>

<script>
    import {dateFormat} from "@/global/clazz";

    export default {
        name: "blogLi3",
        props: {
            item: {
                type: Object
            }
        },
        methods: {

            getTime(date) {
                return dateFormat("YYYY-mm-dd HH:MM:SS", date);
            }
        }
    }
</script>

<style scoped>
   .blogtitle{
       list-style: none;
       width: 100%;
       height:24px;
       border-bottom: 1px black solid;
       cursor: pointer;
       margin-top: 10px;

   }
   .blogtitle:hover{
       color: red;
   }
   .title{
       margin-left: 20px;
       display: inline-block;
   }
    .time{
        float: right;
    }
</style>