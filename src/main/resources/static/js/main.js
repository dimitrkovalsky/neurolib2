window.onload = function () {
    console.log("Page loaded ");
};

$(document).ready(function () {
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
            error: function (xhr, ajaxOptions, thrownError) {
                alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
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
