<template>
    <div class="forming">
        <el-tabs v-model="activeName" @tab-click="handleClick">
            <el-tab-pane label="文章信息" name="info"></el-tab-pane>
            <el-tab-pane label="文章内容" name="content"></el-tab-pane>
            <el-tab-pane label="提交" name="submit"></el-tab-pane>
        </el-tabs>
        <template v-if="activeName==='info'">
            <el-form ref="ruleForm" label-width="100px" class="demo-ruleForm">
                <el-form-item label="文章标题">
                    <div class="title">
                        <el-input placeholder="标题" maxlength="30" show-word-limit v-model="post.title"></el-input>
                    </div>
                </el-form-item>
                <el-form-item label="文章描述">
                    <div class="title">
                        <!--eslint-disable-->
                        <div class="description">
                            <el-input placeholder="描述" type="textarea" v-model="post.description"></el-input>
                        </div>
                    </div>
                </el-form-item>
                <el-form-item label="文章标签">
                    <div class="title">
                        <div class="tags">

                            <el-popover
                                    placement="bottom"
                                    :width="200"
                                    trigger="manual"
                                    v-model:visible="visible"
                            >
                                <template v-if="autocompleteList.length && post.tags.size < 5">
                                    <div class="autoc" @click="addTag(tag1)" v-for="(tag1,index) in autocompleteList">
                                        {{tag1.tagname}}
                                    </div>
                                </template>
                                <template v-else-if="autocompleteList.length === 0">
                                    <div class="autoc">搜索中或者没有对应标签</div>
                                </template>
                                <template v-else>
                                    <div class="autoc">最多五个标签</div>
                                </template>
                                <template #reference>
                                    <el-input placeholder="搜索标签" v-model="tagsear" @input="changing" @focus="focus"
                                              @blur="(visible=false)"></el-input>
                                </template>
                            </el-popover>
                            <el-tag type="success" @close="delTag(tag)" closable v-for="(tag,index) in post.tags">
                                {{tag.tagname}}
                            </el-tag>

                        </div>
                    </div>
                </el-form-item>
            </el-form>
        </template>
        <template v-else-if="activeName === 'content'">
            <el-form>
                <div class="contenting">


                    <div class="left">
                        <el-input
                                type="textarea"
                                :rows="2"
                                placeholder="请输入内容"
                                v-model="post.content">
                        </el-input>
                    </div>
                    <div class="right">
                        <div class="displaying" v-html="post.content"></div>
                    </div>
                </div>


            </el-form>
        </template>
        <template v-else>
            <el-button type="primary">提交文章</el-button>
        </template>
    </div>
</template>

<script>
    import {Post} from "../../../global/clazz";
    import axios, {CancelToken} from "axios";
    import store from '../../../store/index';

    export default {
        name: "forming",
        data() {
            return {
                post: new Post(),
                autocompleteList: [],
                visible: false,
                tagsear: '',
                searchSet: new Set(),
                tagSet: new Set(),
                activeName: 'info'
            }
        },
        beforeRouteEnter() {
            console.log(store);
        },
        mounted() {
            this.post.tags = new Set();
        },
        methods: {
            getTags() {
                this.autocompleteList = [];
                this.searchSet = new Set();
                if (this.autocompleteList.length > 5) {
                    return;
                } else {
                    axios.get("tag/tags").then(data => {
                        if (data.data.errno === 0) {
                            return data.data;
                        } else {
                            return Promise.reject(data.data.message);
                        }
                    }, error => {
                        return Promise.reject(error.toString());
                    }).then(
                        data => {
                            for (let value of data.extend) {
                                this.enSearch(value);
                            }
                            this.autocompleteList = this.autocompleteList.slice(0, 5);
                        }
                    )
                }

            },
            enSearch(tag) {
                // console.log(tag);
                if (this.searchSet.has(tag.tag) || this.tagSet.has(tag.tag)) {
                    console.log(this.searchSet)
                    console.log(tag.tag);
                    console.log(this.searchSet.has(tag.tag));
                    return;
                } else {
                    this.searchSet.add(tag.tag);
                    this.autocompleteList.push(tag);
                }
            },
            focus() {
                this.visible = true;
                if (this.tagsear.length <= 0) {
                    this.getTags();
                }
            },
            changing() {
                // console.log("cccccccccccccccccc")
                var c;
                if (c && c instanceof Function) {
                    c();
                }
                this.autocompleteList = [];
                this.searchSet = new Set();
                axios.get(`tag/name/${this.tagsear}`, {
                    cancelToken: new CancelToken(function executor(cancel) {
                        // An executor function receives a cancel function as a parameter
                        c = cancel;
                    }),
                }).then(data => {
                    if (data.data.errno === 0) {
                        return data.data;
                    } else {
                        return Promise.reject(data.data.message);
                    }
                }, error => {
                    return Promise.reject(error.toString());
                }).then(
                    data => {
                        for (let value of data.extend) {
                            this.enSearch(value);
                        }
                    },
                    error => {
                        console.log(error);
                    }
                )
            },
            addTag(tag) {

                if (this.post.tags.size >= 5) {
                    return;
                } else if (!this.tagSet.has(tag.tag)) {
                    this.post.tags.add(tag);
                    this.autocompleteList = this.autocompleteList.filter(value => {
                        return !value.tag === tag.tag;
                    })
                    this.tagSet.add(tag.tag);
                }


            },
            delTag(tag) {
                this.post.tags.delete(tag);
                this.tagSet.delete(tag.tag);
            }
        }
    }
</script>

<style scoped>
    .forming {
        border-right: 2px solid gray;
        border-left: 2px solid gray;
        padding-left: 20px;
        padding-right: 20px;
        padding-top: 30px;
        width: 100%;
        height: 100%;
        background-color: rgba(255, 237, 237, 0.8);
    }

    .el-form-item >>> .el-form-item__label {
        font-size: 18px;
        color: #000000;
    }
    .left >>> .el-textarea__inner{
        resize: none;
        height: 400px;
    }

    .title {
        width: 80%;
    }

    .description {

    }

    .description >>> .el-textarea__inner {
        resize: none;
        height: 78px;
    }

    .tags >>> .el-input {
        width: 100px;
    }

    .autoc {
        height: 30px;
        font-size: 17px;
        line-height: 30px;
        padding-left: 10px;
    }

    .autoc:hover {
        background-color: #E7E7E7;
    }

    .el-tag {
        margin-left: 10px;
        font-size: 16px;
    }

    .left {
        float: left;
        width: 50%;
    }

    .right {
        float: right;
        width: 50%;
        word-break: break-all;
        word-wrap: break-word;
    }
    .el-button{
        margin: 150px 256px;
    }
</style>