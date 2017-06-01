package jp.co.terraresta.androidlesson.data.model.profile;

import com.google.gson.annotations.Expose;

import jp.co.terraresta.androidlesson.data.model.common.BaseResultData;

/**
 * Created by ooyama on 2017/05/26.
 */

public class ProfileDisplayData extends BaseResultData {

    @Expose
    private int userId;
    @Expose
    private String nickname;
    @Expose
    private int imageId;
    @Expose
    private String imageSize;
    @Expose
    private String imageUrl;
    @Expose
    private int gender;
    @Expose
    private int age;
    @Expose
    private int job;
    @Expose
    private String residence;
    @Expose
    private int personality;
    @Expose
    private int hobby;
    @Expose
    private String aboutMe;
    @Expose
    private int userStatus;
    @Expose
    private String email;
    @Expose
    private String password;

    public int getUserId() {
        return userId;
    }

    public String getNickname() {
        return nickname;
    }

    public int getImageId() {
        return imageId;
    }

    public String getImageSize() {
        return imageSize;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public int getGender() {
        return gender;
    }

    public int getAge() {
        return age;
    }

    public int getJob() {
        return job;
    }

    public String getResidence() {
        return residence;
    }

    public int getPersonality() {
        return personality;
    }

    public int getHobby() {
        return hobby;
    }

    public String getAboutMe() {
        return aboutMe;
    }

    public int getUserStatus() {
        return userStatus;
    }

    public String getEmail() {
        return email;
    }

    public String getPassword() {
        return password;
    }
}
