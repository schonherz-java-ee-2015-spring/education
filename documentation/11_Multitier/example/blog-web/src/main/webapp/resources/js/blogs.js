$(document).ready(function() {
	getBlogs();
});

function getBlogs() {

	jQuery.ajax({
		url : "/blog-web/blog",
		dataType : "json",
		success : function(data) {

			$(data).each(function(index, value) {
				jQuery.ajax({
					url : "resources/template/blog.html",
					success : function(template) {
						template = template.replace('#title', value.title);
						template = template.replace('#text', value.text);
						var find = '#id';
						var re = new RegExp(find, 'g');
						template = template.replace(re, value.id);
						$('#blogs').append(template);
					}
				});
			});
		},

	});

	dialog = $("#dialog-form").dialog({
		autoOpen : false,
		height : 300,
		width : 350,
		modal : true,
		buttons : {
			"Create a blog" : addBlog,
			Cancel : function() {
				dialog.dialog("close");
			}
		},
		close : function() {
			form[0].reset();
			allFields.removeClass("ui-state-error");
		}
	});

	$("#create-blog").button().on("click", function() {
		dialog.dialog("open");
	});

}

function addBlog() {
	console.log('add');
	jQuery.ajax({
		url : "/blog-web/blog",
		type : "POST",
		data : {
			'creator' : 'test user',
			'title' : $("#title").val(),
			'text' : $("#text").val()
		},
		success : function(data) {
			console.log(data);
			getBlogs();
			dialog.dialog("close");
		}
	});
}