package com.b.exchangesync.persistence.repositories;


import com.b.exchangesync.persistence.models.Quote;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.mongodb.repository.Query;

import org.springframework.data.repository.PagingAndSortingRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

/**
 * Created with IntelliJ IDEA.
 * User: sbenner
 * Date: 8/15/14
 * Time: 3:00 AM
 */
@Repository
public interface QuoteRepository extends PagingAndSortingRepository<Quote, Long> {

    Page<Quote> findByName(String name, Pageable pageable);

    Page<Quote> findByNameLike(String name, Pageable pageable);

    @Query(value = "{'name': {$regex : ?0, $options: 'i'}}")
    List<Quote> findItemsByNameRegex(String name);

    @Query(value = "{'name': {$regex : '^?0$', $options: 'i'}}")
    List<Quote> findItemsByNameRegexExactMatch(String name);

    @Query(value = "{'date': {$exists : false}}")
    Page<Quote> findAllWithoutDate(Pageable pageable);

    @Query(value = "{'symbol': ?0, 'date': ?1 }")
    List<Quote>findQuoteBySymbolAndDate(String symbol,String date,Sort sort);

    @Query(value = "{'symbol': ?0, 'ts':{$gt: ?1} }")
    List<Quote>findQuoteBySymbolGtThanTs(String symbol,long ts,Sort sort);

}


