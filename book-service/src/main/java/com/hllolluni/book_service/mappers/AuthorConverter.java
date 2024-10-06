package com.hllolluni.book_service.mappers;

import com.hllolluni.book_service.transfers.AuthorTransfer;
import com.hllolluni.book_service.data.entities.Author;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class AuthorConverter {

    @Autowired
    private ModelMapper modelMapper;

    public Author convertAuthorTransferToAuthor(AuthorTransfer authorTransfer) {
        Author author = modelMapper.map(authorTransfer, Author.class);
        return author;
    }

    public AuthorTransfer convertAuthorEntityToAuthorTransfer(Author author) {
        AuthorTransfer authorTransfer = modelMapper.map(author, AuthorTransfer.class);
        return authorTransfer;
    }
}
