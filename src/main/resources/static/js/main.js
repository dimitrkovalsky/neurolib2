window.onload = function () {
    console.log("Page loaded ")
};

$(document).ready(function() {

    var books = new Bloodhound({
        datumTokenizer: Bloodhound.tokenizers.obj.whitespace('title'),
        queryTokenizer: Bloodhound.tokenizers.whitespace,
        remote: {
            url: '/searchbooktypeahead?q=%QUERY&size=5',
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
            limit:10,
            display: 'title',
            source: books,
        });

    $('#remote .typeahead').bind('typeahead:select', function(ev, suggestion) {
        document.location = "/book/"+suggestion.bookId;
    });


   rating.bind_star();

});

var book = {
    'add': function(bookId) {
        $.ajax({
            url: '/shelf',
            type: 'post',
            data: {'bookId':bookId,
                    '_csrf':_csrf
            },
            dataType: 'html',
            success: function(json) {
                book.changeState(bookId,'delete');
            },
            error: function(xhr, ajaxOptions, thrownError) {
                //alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
                book.changeState(bookId,'error');

            }
        });
    },
    'delete': function(bookId) {
        $.ajax({
            url: '/shelf/'+bookId,
            type: 'delete',
            dataType: 'html',
            beforeSend: function(xhr){
                xhr.setRequestHeader(_csrf_header, _csrf);
            },
            success: function(json) {
                book.changeState(bookId,'add');

            },
            error: function(xhr, ajaxOptions, thrownError) {
                //alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
                book.changeState(bookId,'error');

            }
        });
    },
    'rate': function(bookId,rate) {
        $.ajax({
            url: '/rate',
            type: 'post',
            data: {'bookId':bookId,
                    'rate':rate,
                   '_csrf':_csrf
            },
            dataType: 'html',
            success: function(json) {
                book.changeState(bookId,"delete")
                alert('ok');
            },
            error: function(xhr, ajaxOptions, thrownError) {
                alert(thrownError + "\r\n" + xhr.statusText + "\r\n" + xhr.responseText);
            }
        });
    },
    'changeState': function(bookId,state) {
        if(state=="add"){
            $('#shelf-control-'+bookId+' .book-operation-link').html("<i class=\"material-icons left\">add</i> Добавить ");
            $('#shelf-control-'+bookId+' .book-operation-link').removeClass("red");
            $('#shelf-control-'+bookId+' .book-operation-link').addClass("green");
            $('#shelf-control-'+bookId+' .book-operation-link').attr("onClick","book.add("+bookId+");");
        }
        if(state=="delete"){
            $('#shelf-control-'+bookId+' .book-operation-link').html("<i class=\"material-icons left \">delete</i> Удалить");
            $('#shelf-control-'+bookId+' .book-operation-link').removeClass("green");
            $('#shelf-control-'+bookId+' .book-operation-link').addClass("red");
            $('#shelf-control-'+bookId+' .book-operation-link').attr("onClick","book.delete("+bookId+");");
        }
        if(state=="error"){
            $('#shelf-control-'+bookId+' .book-operation-link').toggleClass("orange");
            setTimeout(function(){
                $('#shelf-control-'+bookId+' .book-operation-link').toggleClass("orange");
            },1000);
        }
    },
};

var rating = {
    'bind_star':function () {
        $('.rating').each(function(){
            var this_ = $(this);
            var bookId = this_.attr('id').split('-')[1];
            this_.on('click', 'i', function(){
                var inputField = $(this).prev();
                inputField.prop('checked', true);
                var rating = inputField.val();

                 book.rate(bookId, rating);
            })
        })
    }
};