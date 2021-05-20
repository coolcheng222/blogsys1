<template>

    <el-container>
        <el-aside width="40px"><i class="el-icon-star-on" />{{item.faved}}</el-aside>
        <el-container>
            <el-container>
                <el-header height="10px" @click="$router.push(`/post/${item.pId}`)"><h3>{{item.title}}</h3></el-header>
                <el-main @click="$router.push(`/post/${item.pId}`)">{{item.description}}</el-main>
                <el-footer height="5px">
                    <div class="tags">

                        标签:
                        <!-- eslint-disable-->
                        <template v-if="item.tags.length">
                            <el-tag effect="plain" size="mini" v-for="tag in item.tags">{{tag.tagname}}</el-tag>
                        </template>
                        <template v-else>
                            无
                        </template>
                    </div>
                    <div>
                        <span class="author" @click="$router.push(`/user/${item.author.uid}`)">{{item.author.username}}</span>
                        <span class="time">{{getTime(item.time)}}</span>
                        <span class="replays" style="color: red"><i class="el-icon-chat-line-round"></i> {{item.reply}}</span>
                    </div>

                </el-footer>
            </el-container>
        </el-container>
    </el-container>
</template>
<script>
    import {dateFormat} from "../../../global/clazz";

    export default {
        name: "blogLi",
        props:{
            item:{
                type: Object
            }
        },
        methods: {

            getTime(date) {
                return dateFormat("YYYY-mm-dd HH:MM", date);
            }
        }
    }
</script>

<style scoped>
    .time{
        margin-left: 10px;
        margin-right: 20px;
    }
    .el-main{
        overflow: hidden;
        text-overflow: ellipsis;
        display: -webkit-box;
        -webkit-line-clamp: 2;
        -webkit-box-orient: vertical;
        word-break: break-all;
        height: 58px;
    }
    .author:hover{
        text-decoration: underline;
        cursor: pointer;
    }
    .el-main:hover{
        cursor: pointer;
    }
    .el-header:hover{
        text-decoration: underline;
        color: red;
        cursor: pointer;
    }
    .el-aside{
        font-size: 20px;
        display: flex;
        justify-content: center;
        align-items: center;
    }
    .el-tag{
        margin-left: 2px;
    }
    .tags{
        margin-bottom:7px;
    }
    .author{
        color: forestgreen;
    }
    .author:hover{
        cursor: pointer;
        text-decoration: underline;
    }
    .time{
        color: #ada5a5;
    }
</style>