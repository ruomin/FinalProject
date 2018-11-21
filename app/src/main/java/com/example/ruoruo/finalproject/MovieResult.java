package com.example.ruoruo.finalproject;

public final class MovieResult {

    private final long id;
    private final String msg;


    public MovieResult(long id, String msg) {
        this.id = id;
        this.msg = msg;
    }

    public long getId() {
        return id;
    }

    public String getMsg() {
        return msg;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        MovieResult that = (MovieResult) o;

        if (id != that.id) return false;
        return msg.equals(that.msg);
    }

    @Override
    public int hashCode() {
        int result = (int) (id ^ (id >>> 32));
        result = 31 * result + msg.hashCode();
        return result;
    }
}
