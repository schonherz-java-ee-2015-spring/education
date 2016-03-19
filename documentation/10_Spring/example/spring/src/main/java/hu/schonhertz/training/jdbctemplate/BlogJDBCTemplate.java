package hu.schonhertz.training.jdbctemplate;

import java.util.List;

import javax.sql.DataSource;

import org.springframework.jdbc.core.JdbcTemplate;

import hu.schonhertz.training.dao.BlogDao;
import hu.schonhertz.training.mapper.BlogMapper;
import hu.schonhertz.training.pojo.Blog;


public class BlogJDBCTemplate implements BlogDao {
  
  private DataSource dataSource;
  private JdbcTemplate jdbcTemplate;
  
  public void setDataSource(DataSource dataSource) {
    this.dataSource = dataSource;
    jdbcTemplate = new JdbcTemplate(this.dataSource);
  }

  @Override
  public List<Blog> getAllBlog() {
    String sql = "SELECT id, creator, title, text FROM public.\"Blog\";";
    List<Blog> blogs = jdbcTemplate.query(sql, new BlogMapper());
    return blogs;
  }

  @Override
  public Blog getBlogById(int id) {
    String sql = "SELECT id, creator, title, text FROM public.\"Blog\" WHERE id = ?;";
    Blog blog = jdbcTemplate.queryForObject(sql, new BlogMapper(), id);
    return blog;
  }

  @Override
  public void createBlog(String creator, String title, String text) {
    String sql = "INSERT INTO public.\"Blog\"(creator, title, text) VALUES (?, ?, ?);";
    jdbcTemplate.update(sql, creator, title, text);
  }

}
