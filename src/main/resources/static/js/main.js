window.onload = function () {
    console.log("Page loaded ");
};

$(document).ready(function () {
    calendar.init_authorborn("#authorborn-calendar","#authorborn-panel");
    addTitles();
    initCarousel();
    var books = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('title'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/searchbooktypeahead?q=%QUERY&size=5',
            wildcard: '%QUERY'
        }
    });

    var authors = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('authorName'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/searchauthortypeahead?q=%QUERY&size=5',
            wildcard: '%QUERY'
        }
    });

    $('#remote .typeahead').typeahead(
        {
            hint: true,
            highlight: true,
            minLength: 2
        }, {
            name: 'books',
            display: 'title',
            source: books,
            templates: {
                header: '<h6 class="books-titles">Книги</h6>'
            }
        },
        {
            name: 'authors',
            display: 'authorName',
            source: authors,
            templates: {
                header: '<h6 class="authors-names">Авторы</h6>'
            }
        });

    $('#remote .typeahead').bind('typeahead:select', function (ev, suggestion) {
        if (typeof suggestion.bookId != 'undefined') {
            document.location = "/book/" + suggestion.bookId;
        } else if (typeof suggestion.authorId != 'undefined') {
            document.location = "/author/" + suggestion.authorId;
        }
    });


    rating.bind_star();

});

$('#comment-module').ready(function () {
    if($('#comment-module').length) {
        comment.initComments();
    }
});

function initCarousel() {
    $(document).ready(function(){
        $('.carousel').carousel();
    });
}

var book = {
    'add': function (bookId) {
        $.ajax({
            url: '/shelf',
            type: 'post',
            data: {
                'bookId': bookId,
                '_csrf': _csrf
            },
            dataType: 'html',
            success: function (json) {
                book.changeState(bookId, 'delete');
            },
            error: function (xhr, ajaxOptions, thrownError) {
                book.changeState(bookId, 'error');
            }
        });
    },
    'delete': function (bookId) {
        $.ajax({
            url: '/shelf/' + bookId,
            type: 'delete',
            dataType: 'html',
            beforeSend: function (xhr) {
                xhr.setRequestHeader(_csrf_header, _csrf);
            },
            success: function (json) {
                book.changeState(bookId, 'add');
            },
            error: function (xhr, ajaxOptions, thrownError) {
                book.changeState(bookId, 'error');
            }
        });
    },
    'rate': function (bookId, rate) {
        $.ajax({
            url: '/rate',
            type: 'post',
            data: {
                'bookId': bookId,
                'rate': rate,
                '_csrf': _csrf
            },
            dataType: 'html',
            success: function (json) {
                book.changeState(bookId, "delete")
            },
            error: function(xhr, ajaxOptions, thrownError) {
                console.log(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
            }
        });
    },
    'changeState': function (bookId, state) {
        var selector = '#shelf-control-' + bookId + ' .book-operation-link';
        if (state == "add") {
            $(selector).html("<i class=\"material-icons left\">add</i> Добавить ");
            $(selector).removeClass("red");
            $(selector).addClass("green");
            $(selector).attr("onClick", "book.add(" + bookId + ");");
        }
        if (state == "delete") {
            $(selector).html("<i class=\"material-icons left \">delete</i> Удалить");
            $(selector).removeClass("green");
            $(selector).addClass("red");
            $(selector).attr("onClick", "book.delete(" + bookId + ");");
        }
        if (state == "error") {
            $(selector).toggleClass("orange");
            setTimeout(function () {
                $('#shelf-control-' + bookId + ' .book-operation-link').toggleClass("orange");
            }, 1000);
        }
    },
};

var rating = {
    'bind_star': function () {
        $('.rating').each(function () {
            var this_ = $(this);
            var bookId = this_.attr('id').split('-')[1];
            this_.on('click', 'i', function () {
                var inputField = $(this).prev();
                inputField.prop('checked', true);
                var rating = inputField.val();

                book.rate(bookId, rating);
            })
        })
    }
};

var calendar = {
    'init':function (selector) {
        $(selector).datetimepicker({
            inline: true,
            locale: "ru",
            format: 'YYYY-MM-DD'
        });
    },
    "add_authorborn_change_event":function (calendar_selector,panel_selector) {
        $(calendar_selector).on('dp.change', function (date,oldDate) {

            var time = moment(date.date);
            calendar.load_author_date(panel_selector,time.format('YYYY-MM-DD'),0);
    });
    },
    'init_authorborn_panel':function (selector) {
        var current_date = moment().format('YYYY-MM-DD');
        calendar.load_author_date(selector,current_date,0);

        $("#load-link a").click(function () {
            calendar.load_author_date(selector,calendar.date,calendar.page+1);
        });
    },
    'init_authorborn':function (calendar_selector,panel_selector) {
        calendar.init(calendar_selector);
        calendar.add_authorborn_change_event(calendar_selector,panel_selector);
        calendar.init_authorborn_panel(panel_selector);
    },
    'load_author_date':function (selector,date,page) {
        $.ajax({
            url: utils.getPageUrl()+'/authorborn',
            type: 'post',
            data: {
                'date': date,
                '_csrf': _csrf,
                'page': page
            },
            dataType: 'json',
            success: function (json) {
                calendar.update_load_button(json);
                var formattedAuthors =  calendar.formatAuthors(json.data);
                calendar.apply_to_panel(selector+' .collection',date,page,formattedAuthors);
            },
            error: function (xhr, ajaxOptions, thrownError) {
                console.log(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
            }
        });
    },
    'formatAuthor':function (authorObj) {
        var messageTemplate =
            '<li class="collection-item avatar col-md-3">'+
                '<img class="circle" width="50px" src="/api/images/author/'+authorObj.id+'"/>'+
                '<span class="title">'+
                    '<a style="font-size: larger" href="/author/'+authorObj.id+'">'+
                        '<span class="media-heading">'+authorObj.authorName+'</span>'+
                    '</a>'+
                '</span>'+
            '</li>';
        return messageTemplate;
    },
    'formatAuthors':function (jsonArray) {
        var resultString = '';
        $.each(jsonArray,function () {
            resultString=resultString+calendar.formatAuthor(this) ;
        });
        return resultString;
    },
    'apply_to_panel':function (selector,date,page,data) {
        calendar.page=page;
        if(calendar.date===undefined||calendar.date!=date){
            calendar.date = date;
            calendar.page = 0;
            $(selector).html("");
        }else {
            calendar.date = date;
            calendar.page = page;
        }
        $(selector).append(data);
    },

    'update_load_button':function (json) {
        if(json.available==false){
            $("#load-link a").attr("style", "visibility: hidden");
        }else{
            $("#load-link a").attr("style", "visibility: visible");
        }
    }
};
    var comment = {
        lastCommentTime : 0,
        'loadComments': function(afterTime) {
            $.ajax({
                url: utils.getPageUrl()+'/comments',
                type: 'get',
                data: {
                    'after': afterTime,
                    '_csrf': _csrf
                },
                dataType: 'json',
                success: function (json) {
                    var formattedMessage =  comment.formatComments(json);
                    comment.prependComments(formattedMessage);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
                }
            });
        },

        'doComment': function(commentText) {
            $.ajax({
                url: utils.getPageUrl()+'/comments',
                type: 'post',
                data: {
                    'comment': commentText,
                    '_csrf': _csrf
                },
                dataType: 'html',
                success: function (json) {
                    comment.loadComments(comment.lastCommentTime);
                    comment.clearCommentInput();
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
                }
            });
        },
        'delete': function(commentId) {
            $.ajax({
                url: utils.getPageUrl()+'/comments/'+commentId,
                type: 'delete',
                beforeSend: function(xhr){
                    xhr.setRequestHeader(_csrf_header, _csrf);
                },
                dataType: 'html',
                success: function (json) {
                    comment.markAsDeleted(commentId);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
                }
            });
        },
        'restore': function(commentId) {
            $.ajax({
                url: utils.getPageUrl()+'/comments/'+commentId,
                type: 'put',
                beforeSend: function(xhr){
                    xhr.setRequestHeader(_csrf_header, _csrf);
                },
                dataType: 'json',
                success: function (json) {
                    comment.doRestore(commentId,json);
                },
                error: function (xhr, ajaxOptions, thrownError) {
                    console.log(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
                }
            });
        },

        'markAsDeleted':function (commentId) {
            var deletedTemplate = '<blockquote><p>Комментарий удален. <a  href="#!" onclick="comment.restore('+commentId+')">Восстановить</a></p></blockquote>';
            var comment = $('#comment-'+commentId);
            var animationEvent = 'webkitAnimationEnd oanimationend msAnimationEnd animationend';
            comment.children().addClass('removed-item').one(animationEvent, function(e) {
                $(this).remove();
            });
            comment.addClass('restored-item').one(animationEvent, function(e) {
                $(this).html(deletedTemplate);
                $(this).removeClass('restored-item');

                setTimeout(function(){
                    comment.addClass('removed-item').one(animationEvent, function(e) {
                        $(this).remove();
                    });
                },120000);
            });
        },

        'doRestore':function (commentId,json) {
            var commentObj = $('#comment-'+commentId);
            var restoredMessage = comment.formatComment(json);
            var animationEvent = 'webkitAnimationEnd oanimationend msAnimationEnd animationend';
            commentObj.children().addClass('removed-item').one(animationEvent, function(e) {
                $(this).remove();
            });

            commentObj.addClass('restored-item').one(animationEvent, function(e) {
                $(this).html($(restoredMessage).children());
            });
        },


        'formatComment':function (commentObj) {
            var postDate = utils.dateFromUnix(commentObj.createTime);

            var deleteTemplate = '<a href="#!" onclick="comment.delete('+commentObj.id+');"> <i style="float:right" class="material-icons">close</i> </a>';
            var noActionTemplate ='';

            if(commentObj.isOwner){
                var action = deleteTemplate;
            }else{
                var action = noActionTemplate;
            }

            var messageTemplate =
                '<div class="row" id="comment-'+commentObj.id+'">'+
                '<div class="col-md-1">'+
                '<div class="thumbnail">'+
                '<img class="img-responsive user-photo" src="https://ssl.gstatic.com/accounts/ui/avatar_2x.png">'+
                '</div>'+
                '</div>'+
                '<div class="col-md-11">'+
                '<div class="panel panel-default">'+
                '<div class="panel-heading">'+
                '<strong>'+commentObj.userName+' </strong>'+
                '<span class="text-muted">'+postDate+'</span>'+
                action+
                '</div>'+
                '<div class="panel-body">'+
                '<span style="word-wrap: break-word">'+commentObj.comment+'</span>'+
                '</div>'+
                '</div>'+
                '</div>'+
                '</div>';
            return messageTemplate;
        },
        'formatComments':function (jsonArray) {
            var resultString = "";
            $.each(jsonArray,function () {
                resultString=comment.formatComment(this) + resultString;
                comment.lastCommentTime = this.createTime;
            });
            return resultString;
        },


        'prependComments':function (html) {
            $('#comments-block').prepend(html);
        },

        'clearCommentInput':function () {
            var commentInput = $('#comment-input');
            commentInput.val("");
            commentInput.removeAttr('style');
        },

        'initComments':function () {

            comment.loadComments( comment.lastCommentTime);

            setInterval(function () {
                comment.loadComments(comment.lastCommentTime);
            }, 15000);


            $('#send-comment-btn').click(function () {
                var textArea = $('#comment-input');
                if(textArea.val().length>2&&textArea.val().length<=512){
                    comment.doComment(textArea.val());
                }else{
                    textArea.toggleClass('invalid valid');
                    setTimeout(function(){
                        textArea.toggleClass('invalid valid');
                    },3000);
                }

            });
        }

    };

    var utils = {
        'dateFromUnix':function (timestamp) {
            var date = new Date(timestamp);
            return date.toUTCString();
        },
        'getPageUrl':function () {
            var fullUrl = $(document)[0].URL;
            return fullUrl.split("#")[0].split("!")[0].split("?")[0];
        }
    };

function addTitles() {
    $("a[data-book-tooltip]").each(function () {
        var bookId = $(this).attr("data-book-tooltip");
        var title = $(this).attr("data-book-title");
        var image = "<img class='popup-image' src='/api/images/book/" + bookId + "'>";
        var popup = "<div style='height: 200px'><b>" + title + "</b><br>" + image + "</div>";
        $(this).popover({
            html: true,
            trigger: 'hover',
            content: function () {
                return popup;
            }
        });
        console.log("Added title to " + bookId);
    });

}
