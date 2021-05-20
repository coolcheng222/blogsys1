export class User {
    uid = null;
    username = null;
    password = null;
    email = null;
    kaptcha = null;
}

export class Page {
    endRow
    hasNextPage
    hasPreviousPage
    isFirstPage
    isLastPage
    navigateFirstPage
    navigateLastPage
    navigatePages
    navigatepageNums
    nextPage
    pageNum
    pageSize
    pages
    prePage
    size
    startRow
    total
}

export class Post {
    author
    content
    description
    pId
    tags = new Set()
    time
    title
    uid
    faved
    reply
}

export class Reply {
    reid;
    post;
    user;
    content;
    time;
    stars;
    stared;
}

export class UserInfo {
    posts
    rolename
    subbed
    subing
    uid
    username
}

export class Tag {
    tag;
    tagname;
    posts;
    list;
}

export class Faving {
    pid;
    res;
}

export class Faved {
    pid;
    users
    count
}

export class Fav{
    uid;
    fav;
    count;
}

export function dateFormat(fmt, date1) {
    let date = new Date(date1);
    let ret;
    const opt = {
        "Y+": date.getFullYear().toString(),        // 年
        "m+": (date.getMonth() + 1).toString(),     // 月
        "d+": date.getDate().toString(),            // 日
        "H+": date.getHours().toString(),           // 时
        "M+": date.getMinutes().toString(),         // 分
        "S+": date.getSeconds().toString()          // 秒
        // 有其他格式化字符需求可以继续添加，必须转化成字符串
    };
    for (let k in opt) {
        ret = new RegExp("(" + k + ")").exec(fmt);
        if (ret) {
            fmt = fmt.replace(ret[1], (ret[1].length == 1) ? (opt[k]) : (opt[k].padStart(ret[1].length, "0")))
        }
    }
    return fmt;
}