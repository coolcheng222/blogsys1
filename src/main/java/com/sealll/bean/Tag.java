package com.sealll.bean;

public class Tag {
    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag.tag
     *
     * @mbg.generated Sun Mar 28 15:51:14 CST 2021
     */
    private String tag;

    /**
     *
     * This field was generated by MyBatis Generator.
     * This field corresponds to the database column tag.tagname
     *
     * @mbg.generated Sun Mar 28 15:51:14 CST 2021
     */
    private String tagname;

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag.tag
     *
     * @return the value of tag.tag
     *
     * @mbg.generated Sun Mar 28 15:51:14 CST 2021
     */
    public String getTag() {
        return tag;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag.tag
     *
     * @param tag the value for tag.tag
     *
     * @mbg.generated Sun Mar 28 15:51:14 CST 2021
     */
    public void setTag(String tag) {
        this.tag = tag == null ? null : tag.trim();
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method returns the value of the database column tag.tagname
     *
     * @return the value of tag.tagname
     *
     * @mbg.generated Sun Mar 28 15:51:14 CST 2021
     */
    public String getTagname() {
        return tagname;
    }

    /**
     * This method was generated by MyBatis Generator.
     * This method sets the value of the database column tag.tagname
     *
     * @param tagname the value for tag.tagname
     *
     * @mbg.generated Sun Mar 28 15:51:14 CST 2021
     */
    public void setTagname(String tagname) {
        this.tagname = tagname == null ? null : tagname.trim();
    }
}