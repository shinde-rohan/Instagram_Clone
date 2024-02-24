package com.example.instagram.models

class Reel {
    var reelUrl : String = ""
    var caption : String = ""
    var profileLink : String?= null

    constructor(postUrl: String, caption: String) {
        this.reelUrl = postUrl
        this.caption = caption
    }

    constructor()
    constructor(reelUrl: String, caption: String, profileLink: String) {
        this.reelUrl = reelUrl
        this.caption = caption
        this.profileLink = profileLink
    }
}