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

});